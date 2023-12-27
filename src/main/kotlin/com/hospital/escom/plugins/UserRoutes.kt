package com.hospital.escom.plugins

import com.hospital.escom.domain.UserRole
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.createRouteScopedPlugin
import io.ktor.server.auth.AuthenticationChecked
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.uri
import io.ktor.server.response.respond
import io.ktor.server.response.respondText

val AuthenticationWithRolesPlugin =
	createRouteScopedPlugin<Map<UserRole, Set<String>>>(
		name = "AuthWithRoles",
		createConfiguration = {
			hashMapOf(
				UserRole.Patient to setOf("/otherRoute"),
				UserRole.Doctor to setOf("/home"),
				UserRole.Receptionist to setOf("/otherRoute")
			)
		}
	) {
		on(AuthenticationChecked) { call ->
			val principal = (call.principal<JWTPrincipal>()) ?: kotlin.run {
				call.respond(status = HttpStatusCode.Forbidden, message = "You are not authenticated")
				return@on
			}
			val role = checkNotNull(principal.getClaim("role", UserRole::class))
			val uri = call.request.uri
			if (uri !in pluginConfig.getValue(role))
				call.respondText(status = HttpStatusCode.Forbidden) {
					"$role\'s are not allowed this endpoint"
				}
		}
	}