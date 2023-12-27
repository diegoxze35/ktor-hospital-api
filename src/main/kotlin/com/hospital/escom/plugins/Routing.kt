package com.hospital.escom.plugins

import com.hospital.escom.adapter.routes.homeUserData
import com.hospital.escom.adapter.routes.login
import com.hospital.escom.application.port.`in`.LoginPortService
import io.ktor.server.application.Application
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
	routing {
		route("/api") {
			val loginService: LoginPortService by inject()
			login(loginService)
			homeUserData()
		}
	}
}
