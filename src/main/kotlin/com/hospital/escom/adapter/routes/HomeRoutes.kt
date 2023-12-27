package com.hospital.escom.adapter.routes

import com.hospital.escom.plugins.AuthenticationWithRolesPlugin
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.homeUserData() {
	authenticate {
		install(AuthenticationWithRolesPlugin)
		get("/home") {
			call.respond(status = HttpStatusCode.OK, message = "Auth")
		}
	}
}