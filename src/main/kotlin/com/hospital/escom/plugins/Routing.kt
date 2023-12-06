package com.hospital.escom.plugins

import com.hospital.escom.adapter.persistence.entity.UserEntity
import com.hospital.escom.adapter.routes.login
import com.hospital.escom.domain.User
import com.hospital.escom.domain.UserGender
import com.hospital.escom.domain.UserRole
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
	routing {
		login()
		get("/") {
			val response =
				transaction {
					UserEntity.all().map {
						User(
							name = it.name,
							paternal = it.paternal,
							maternal = it.maternal,
							username = it.username,
							password = it.password,
							role = when (it.role.roleName) {
								"Paciente" -> UserRole.Patient
								"Doctor" -> UserRole.Doctor
								else -> UserRole.Receptionist
							},
							gender = if (it.gender.gender == "Masculino")
								UserGender.Male
							else
								UserGender.Female
						)
					}
				}
			call.respond(HttpStatusCode.OK, response)
		}
	}
}
