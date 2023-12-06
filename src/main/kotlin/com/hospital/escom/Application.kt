package com.hospital.escom

import com.hospital.escom.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
	io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
	configureSecurity()
	configureSerialization()
	configureDatabases()
	configureRouting()
}
