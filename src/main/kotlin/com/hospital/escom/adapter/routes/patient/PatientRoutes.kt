package com.hospital.escom.adapter.routes.patient

import com.hospital.escom.adapter.persistence.entity.ConsultingRoomEntity
import com.hospital.escom.adapter.persistence.entity.DoctorEntity
import com.hospital.escom.adapter.persistence.table.ConsultingRoomTable
import com.hospital.escom.adapter.persistence.table.DoctorTable
import com.hospital.escom.adapter.persistence.table.MedicalCiteTable
import com.hospital.escom.adapter.persistence.table.MedicalSpecialityTable
import com.hospital.escom.adapter.persistence.table.UserTable
import com.hospital.escom.application.port.`in`.medicalinformation.MedicalInformationService
import com.hospital.escom.domain.CancelCiteRequest
import com.hospital.escom.domain.DoctorWithSpecialityResponse
import com.hospital.escom.domain.EditMedicalCiteRequest
import com.hospital.escom.domain.ScheduleCiteResponse
import com.hospital.escom.domain.ScheduleMedicalCiteRequest
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.NullBody
import io.ktor.server.application.call
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.plugins.CannotTransformContentToTypeException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import java.util.*
import org.jetbrains.exposed.sql.Concat
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.jodatime.DateColumnType
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.format.DateTimeFormat

fun Route.patientRoutes(service: MedicalInformationService) {
	val formatter = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm a").withLocale(Locale.ENGLISH)
	get("/medicalAppointments") {
		val patientId = call.principal<JWTPrincipal>()?.getClaim("userId", Int::class) ?: kotlin.run {
			call.respond(status = HttpStatusCode.Forbidden, message = String())
			return@get
		}
		val statusId = call.request.queryParameters["statusId"]?.toIntOrNull() ?: 2
		if (statusId !in 1 .. 3) {
			call.respond(status = HttpStatusCode.BadRequest, message = NullBody)
			return@get
		}
		val allCites = service.allMedicalAppointments(patientId, statusId)
		call.respond(status = HttpStatusCode.OK, allCites)
	}
	
	post("/scheduleMedicalAppointment") {
		val request = try {
			call.receive<ScheduleMedicalCiteRequest>()
		} catch (e: CannotTransformContentToTypeException) {
			call.respond(status = HttpStatusCode.BadRequest, message = e.message.orEmpty())
			return@post
		}
		val patientId = call.principal<JWTPrincipal>()?.getClaim("userId", Int::class) ?: kotlin.run {
			call.respond(status = HttpStatusCode.Forbidden, message = String())
			return@post
		}
		
		//val dateTimeFormatter =
		newSuspendedTransaction {
			val consultingRoom = ConsultingRoomEntity.find {
				ConsultingRoomTable.number eq request.roomNumber
			}.single()
			val citeId = MedicalCiteTable.insertAndGetId {
				it[this.patientId] = patientId
				it[doctorId] = request.doctorId
				it[datetime] = formatter.parseDateTime(request.date)
				it[roomId] = consultingRoom.id
				it[isCanceled] = false
			}
			val response = ScheduleCiteResponse(
				citeId = citeId.value,
				specialityName = DoctorEntity[request.doctorId].speciality.specialityName,
				dateTime = request.date,
				roomNumber = consultingRoom.roomNumber
			)
			call.respond(status = HttpStatusCode.Created, message = response)
		}
	}
	
	put ("/updateMedicalAppointment") {
		val request = try {
			call.receive<EditMedicalCiteRequest>()
		} catch (e: CannotTransformContentToTypeException) {
			call.respond(status = HttpStatusCode.BadRequest, message = e.message.orEmpty())
			return@put
		}
		val patientId = call.principal<JWTPrincipal>()?.getClaim("userId", Int::class) ?: kotlin.run {
			call.respond(status = HttpStatusCode.Forbidden, message = String())
			return@put
		}
		newSuspendedTransaction {
			val newConsultingRoom = ConsultingRoomEntity.find {
				ConsultingRoomTable.number eq request.newRoomNumber
			}.single()
			MedicalCiteTable.update(
				where = {
					MedicalCiteTable.id eq request.citeId and(MedicalCiteTable.patientId eq patientId)
				}
			) {
				it[datetime] = formatter.parseDateTime(request.newDate)
				it[roomId] = newConsultingRoom.id
			}
			call.respond(status = HttpStatusCode.Accepted, message = request.citeId)
		}
	}
	
	put("/cancelCite") {
		val patientId = call.principal<JWTPrincipal>()?.getClaim("userId", Int::class) ?: kotlin.run {
			call.respond(status = HttpStatusCode.Forbidden, message = String())
			return@put
		}
		val citeId = try {
			call.receive<CancelCiteRequest>().citeId
		} catch (e: CannotTransformContentToTypeException) {
			call.respond(status = HttpStatusCode.NotAcceptable, message = e.message.orEmpty())
			return@put
		}
		newSuspendedTransaction {
			val citeIdUpdated = MedicalCiteTable.update(
				where = { MedicalCiteTable.id eq citeId and (MedicalCiteTable.patientId eq patientId) }
			) {
				it[isCanceled] = true
			}
			call.respond(status = HttpStatusCode.Accepted, message = citeIdUpdated)
		}
	}
	
	get("/medicalAppointmentTimes") {
		val date: String?
		val doctorId: Int?
		val roomNumber: Int?
		with(call.request.queryParameters) {
			date = this["date"]
			doctorId = this["doctorId"]?.toIntOrNull()
			roomNumber = this["roomNumber"]?.toIntOrNull()
		}
		if (date == null || doctorId == null || roomNumber == null) {
			call.respond(status = HttpStatusCode.BadRequest, message = NullBody)
			return@get
		}
		newSuspendedTransaction {
			val columnName = "hora"
			val hours = exec(
				stmt = "exec horas_disponibles_citas_sp ?, ?, ?",
				args = listOf(
					DateColumnType(time = false) to date,
					IntegerColumnType() to roomNumber,
					IntegerColumnType() to doctorId
				)
			) {
				buildList {
					while (it.next()) add(it.getString(columnName))
				}
			}.orEmpty()
			call.respond(status = HttpStatusCode.OK, message = hours)
		}
	}
	
	get("/allRoomNumbers") {
		val roomNumbers = transaction {
			ConsultingRoomTable.slice(ConsultingRoomTable.number).selectAll().map {
				it[ConsultingRoomTable.number]
			}
		}
		call.respond(status = HttpStatusCode.OK, message = roomNumbers)
	}
	
	get("/allDoctorsBySpecialities") {
		val specialityName = call.request.queryParameters["specialityName"] ?: kotlin.run {
			call.respond(status = HttpStatusCode.BadRequest, message = NullBody)
			return@get
		}
		val response = mutableListOf<DoctorWithSpecialityResponse>()
		newSuspendedTransaction {
			val fullNameColumn = Concat(
				separator = " ", UserTable.name, UserTable.paternal, UserTable.maternal
			).alias("fullName")
			(UserTable innerJoin DoctorTable innerJoin MedicalSpecialityTable)
				.slice(
					fullNameColumn,
					DoctorTable.id,
					MedicalSpecialityTable.speciality
				)
				.select {
					MedicalSpecialityTable.speciality eq specialityName
				}.forEach {
					response.add(
						DoctorWithSpecialityResponse(
							doctorId = it[DoctorTable.id].value,
							fullName = it[fullNameColumn]
						)
					)
				}
		}
		call.respond(status = HttpStatusCode.OK, message = response)
	}
}