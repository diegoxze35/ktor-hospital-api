package com.hospital.escom.adapter.routes.patient

import com.hospital.escom.plugins.AuthenticationWithRolesPlugin
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.patientRoutes() {
	authenticate {
		install(AuthenticationWithRolesPlugin)
		get("/medicalAppointments") {
			val patientId = call.principal<JWTPrincipal>()?.getClaim("userId", Int::class) ?: kotlin.run {
				call.respond(status = HttpStatusCode.Forbidden, message = String())
				return@get
			}
		}
	}
}