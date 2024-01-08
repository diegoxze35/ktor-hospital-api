package com.hospital.escom.adapter.routes.doctor

import com.hospital.escom.application.port.`in`.medicalinformation.MedicalInformationService
import com.hospital.escom.domain.MedicalPrescription
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.NullBody
import io.ktor.server.application.call
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

fun Route.doctorRoutes(service: MedicalInformationService) {
	
	get("/doctorMedicalAppointments") {
		val doctorId = call.principal<JWTPrincipal>()?.getClaim<Int>("userId", Int::class) ?: kotlin.run {
			call.respond(status = HttpStatusCode.Forbidden, message = NullBody)
			return@get
		}
		val statusId = call.request.queryParameters["statusId"]?.toIntOrNull() ?: 2
		if (statusId !in 1 .. 3) {
			call.respond(status = HttpStatusCode.BadRequest, message = NullBody)
			return@get
		}
		val cites = service.allMedicalAppointments(userId = doctorId, statusId = statusId)
		call.respond(
			HttpStatusCode.OK,
			message = cites
		)
	}
	
	get("/generateMedicalPrescription") {
		val patientFullName = call.request.queryParameters["patientFullName"] ?: kotlin.run {
			call.respond(status = HttpStatusCode.BadRequest, message = NullBody)
			return@get
		}
		newSuspendedTransaction {
			val prescription = exec(
				stmt = "select * from Generar_receta (?)",
				args = listOf(
					VarCharColumnType() to patientFullName
				)
			) {
				buildList {
					while (it.next()) {
						add(
							MedicalPrescription(
								folio = it.getInt("NumeroReceta"),
								date = it.getTimestamp("Fecha"),
								patientFullName = it.getString("nombre_Paciente"),
								doctorFullName = it.getString("nombre_Doctor"),
								diagnostic = it.getString("Diagnostico"),
								medicamentName = it.getString("Medicamento"),
								medicamentUnit = it.getString("Medida"),
								duration = it.getString("Duracion"),
								period = it.getString("Periodo")
							)
						)
					}
				}
			}.orEmpty()
			call.respond(status = HttpStatusCode.OK, message = prescription)
		}
	}
}