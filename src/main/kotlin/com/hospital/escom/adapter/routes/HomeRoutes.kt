package com.hospital.escom.adapter.routes

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Routing
fun Routing.homeUserData() {
	authenticate {
	
	}
}