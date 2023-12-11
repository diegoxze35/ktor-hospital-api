package com.hospital.escom.adapter.routes

import com.hospital.escom.adapter.persistence.table.PatientTable
import com.hospital.escom.domain.UserRole
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

fun Routing.homeUserData() {
	authenticate {
		get("/home/{userId}/{userRole}") {
			val userId = call.parameters["userId"]!!.toInt()
			val userRol = UserRole.valueOf(call.parameters["userRole"]!!)
			
			when (userRol) {
				UserRole.Patient -> {
					newSuspendedTransaction {
						val data = PatientTable.select {
							PatientTable.userId eq userId
						}.single()[PatientTable.curp]
						call.respond(status = HttpStatusCode.OK, message = data)
					}
				}
				
				UserRole.Doctor -> {
				
				}
				
				UserRole.Receptionist -> {
				
				}
			}
			call.respond(status = HttpStatusCode.OK, message = "Auth")
		}
	}
}