package com.hospital.escom.adapter.routes

import com.hospital.escom.application.port.`in`.LoginPortService
import com.hospital.escom.domain.UserCredentials
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post

fun Routing.login(loginService: LoginPortService) {
	post("/login") {
		val credentials = call.receive<UserCredentials>()
		loginService.login(credentials)?.let {
				call.respond(status = HttpStatusCode.OK, message = it)
		} ?: call.respond(
			status = HttpStatusCode.NotAcceptable,
			message = "Bad Credentials"
		)
	}
}