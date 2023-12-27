package com.hospital.escom.adapter.routes

import com.hospital.escom.application.port.`in`.LoginPortService
import com.hospital.escom.domain.UserCredentials
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.login(loginService: LoginPortService) {
	post("/login") {
		val credentials = call.receive<UserCredentials>()
		val authUser = loginService.login(credentials)
		val status =
			if (authUser.token != null && authUser.user != null) HttpStatusCode.OK
			else HttpStatusCode.Unauthorized
		call.respond(
			status = status,
			message = authUser
		)
	}
}