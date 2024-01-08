package com.hospital.escom.adapter.routes.userpatientreceptionistcommon

import com.hospital.escom.adapter.persistence.entity.MedicalSpecialityEntity
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.patientReceptionistCommon() {
	get("/allSpecialities") {
		val names = transaction {
			MedicalSpecialityEntity.all().map { it.toDomain() }
		}
		call.respond(HttpStatusCode.OK, names)
	}
}