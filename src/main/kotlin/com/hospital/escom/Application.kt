package com.hospital.escom

import com.hospital.escom.plugins.*
import io.ktor.server.application.Application

fun main(args: Array<String>) {
	io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
	configureSecurity()
	configureSerialization()
	configureDependencyInjection()
	configureDatabases()
	configureRouting()
}
