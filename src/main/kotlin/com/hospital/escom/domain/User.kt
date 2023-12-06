package com.hospital.escom.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
	val name: String,
	val paternal: String,
	val maternal: String,
	val username: String,
	val password: String,
	val role: UserRole,
	val gender: UserGender,
)
