package com.hospital.escom.domain

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticatedUser(
	val token: String,
	val user: User
)
