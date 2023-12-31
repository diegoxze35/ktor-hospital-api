package com.hospital.escom.adapter.routes.receptionist.domain

import com.hospital.escom.domain.user.User
import kotlinx.serialization.Serializable

@Serializable
data class AddUserRequest(
	val password: String,
	val newUser: User
)
