package com.hospital.escom.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*

private const val URL = "url"
private const val DATABASE_USER = "user"
private const val DATABASE_PASSWORD = "password"
private const val DATABASE_CONFIG = "database"

fun Application.configureDatabases() {
	val databaseConfiguration = environment.config.config(DATABASE_CONFIG)
	Database.connect(
		url = databaseConfiguration.property(URL).getString(),
		user = System.getenv(DATABASE_USER),
		password = System.getenv(DATABASE_PASSWORD)
	)
}