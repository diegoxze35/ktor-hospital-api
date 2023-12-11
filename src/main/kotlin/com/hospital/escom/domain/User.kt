package com.hospital.escom.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
	val id: Int,
	val name: String,
	val paternal: String,
	val maternal: String,
	val role: UserRole,
	val gender: UserGender,
)
