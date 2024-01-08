package com.hospital.escom.adapter.routes.receptionist

import com.hospital.escom.adapter.persistence.entity.DoctorEntity
import com.hospital.escom.adapter.persistence.entity.PatientEntity
import com.hospital.escom.adapter.persistence.table.DoctorTable
import com.hospital.escom.adapter.persistence.table.MedicalCiteTable
import com.hospital.escom.adapter.persistence.table.PatientTable
import com.hospital.escom.adapter.persistence.table.TicketTable
import com.hospital.escom.adapter.persistence.table.TotalTicket
import com.hospital.escom.adapter.persistence.table.UserTable
import com.hospital.escom.adapter.routes.receptionist.domain.AddUserRequest
import com.hospital.escom.adapter.routes.receptionist.domain.EmitTicketRequest
import com.hospital.escom.adapter.routes.receptionist.domain.UpdateIsActiveUserRequest
import com.hospital.escom.application.port.`in`.user.UserService
import com.hospital.escom.domain.Ticket
import com.hospital.escom.domain.TicketResponse
import com.hospital.escom.domain.UpdatableUser
import com.hospital.escom.domain.UserResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.plugins.CannotTransformContentToTypeException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import org.jetbrains.exposed.sql.Concat
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

fun Route.receptionistRoutes(userService: UserService) {
	post("/addUser") {
		val (user, password) = try {
			call.receive<AddUserRequest>()
		} catch (e: CannotTransformContentToTypeException) {
			call.respond(status = HttpStatusCode.BadRequest, message = e.message.orEmpty())
			return@post
		}
		val userCreated = userService.addUser(user, password)
		call.respond(status = HttpStatusCode.Created, message = userCreated)
	}
	
	get("/allUsers") {
		newSuspendedTransaction {
			val users: List<UserResponse> = PatientEntity.all().map {
				UserResponse(
					id = it.id.value,
					userType = UpdatableUser.Patient,
					fullName = "${it.user.name} ${it.user.paternal} ${it.user.maternal}",
					isActive = it.isActive
				)
			} + DoctorEntity.all().map {
				UserResponse(
					id = it.id.value,
					userType = UpdatableUser.Doctor,
					fullName = "${it.user.name} ${it.user.paternal} ${it.user.maternal}",
					isActive = it.isActive
				)
			}
			call.respond(status = HttpStatusCode.OK, message = users)
		}
	}
	
	put("/updateIsActive") {
		val updatableUserRequest = try {
			call.receive<UpdateIsActiveUserRequest>()
		} catch (e: CannotTransformContentToTypeException) {
			call.respond(status = HttpStatusCode.BadRequest, message = e.message.orEmpty())
			return@put
		}
		newSuspendedTransaction {
			var cites: Long = 0
			val canModifyStatus = if (!updatableUserRequest.newIsActive) { //if new state is false *down*
				val columnToSearch = when (updatableUserRequest.userType) {
					UpdatableUser.Patient -> MedicalCiteTable.patientId
					UpdatableUser.Doctor -> MedicalCiteTable.doctorId
				}
				MedicalCiteTable.select(
					where = {
						columnToSearch eq updatableUserRequest.userId and (MedicalCiteTable.status eq 2)
					}
				).count().also { cites = it } == 0L
			} else true
			if (!canModifyStatus) {
				call.respond(status = HttpStatusCode.NotAcceptable, message = cites)
				return@newSuspendedTransaction
			}
			when (updatableUserRequest.userType) {
				UpdatableUser.Patient -> {
					PatientTable.update(
						where = {
							PatientTable.id eq updatableUserRequest.userId
						}
					) {
						it[isActive] = updatableUserRequest.newIsActive
					}
				}
				
				UpdatableUser.Doctor -> {
					DoctorTable.update(
						where = {
							DoctorTable.id eq updatableUserRequest.userId
						}
					) {
						it[isActive] = updatableUserRequest.newIsActive
					}
				}
			}
			call.respond(status = HttpStatusCode.Accepted, updatableUserRequest.userId)
		}
	}
	
	get("/allTickets") {
		newSuspendedTransaction {
			val columnFullName = Concat(separator = " ", UserTable.name, UserTable.paternal, UserTable.maternal)
			val allPatients = TotalTicket.slice(TotalTicket.id).selectAll().map {
				it[TotalTicket.id].value
			}
			val finalResponse = mutableListOf<TicketResponse>()
			allPatients.forEach { patientId ->
				val allTickets = TicketTable.select {
					TicketTable.patientId eq patientId
				}.map {
					Ticket(
						ticketId = it[TicketTable.id].value,
						concept = it[TicketTable.concept],
						amount = it[TicketTable.amount]
					)
				}
				finalResponse.add(
					TicketResponse(
						patientId = patientId,
						patientFullName = UserTable.slice(columnFullName).select { UserTable.id eq patientId }
							.single()[columnFullName],
						tickets = allTickets,
						total = TotalTicket.slice(TotalTicket.totalTicket).select {
							TotalTicket.id eq patientId
						}.single()[TotalTicket.totalTicket]
					)
				)
			}
			call.respond(status = HttpStatusCode.OK, message = finalResponse)
		}
	}
	
	delete("/emitTicket") {
		val request = try {
			call.receive<EmitTicketRequest>()
		} catch (e: CannotTransformContentToTypeException) {
			call.respond(status = HttpStatusCode.NotAcceptable, message = e.message.orEmpty())
			return@delete
		}
		newSuspendedTransaction {
			TotalTicket.deleteWhere {
				id eq request.patientId
			}
			TicketTable.deleteWhere {
				patientId eq request.patientId
			}
			call.respond(status = HttpStatusCode.Accepted, message = request.patientId)
		}
	}
}