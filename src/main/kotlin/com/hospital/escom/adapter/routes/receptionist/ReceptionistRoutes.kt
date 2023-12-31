package com.hospital.escom.adapter.routes.receptionist

import com.hospital.escom.adapter.routes.receptionist.domain.AddUserRequest
import com.hospital.escom.application.port.`in`.user.UserService
import com.hospital.escom.plugins.AuthenticationWithRolesPlugin
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.CannotTransformContentToTypeException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.receptionistRoutes(userService: UserService) {
	authenticate {
		install(AuthenticationWithRolesPlugin)
		post("/addUser") {
			val (password, user) = try {
				call.receive<AddUserRequest>()
			} catch (e: CannotTransformContentToTypeException) {
				call.respond(status = HttpStatusCode.BadRequest, message = e.message.orEmpty())
				return@post
			}
			val userCreated = userService.addUser(user, password)
			call.respond(status = HttpStatusCode.Created, message = userCreated)
		}
	}
}