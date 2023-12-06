package com.hospital.escom.adapter.routes

import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

fun Routing.login() {
	post("/login") {
		newSuspendedTransaction {
		
		}
	}
}