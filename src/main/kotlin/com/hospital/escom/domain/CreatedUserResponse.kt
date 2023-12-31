package com.hospital.escom.domain

import com.hospital.escom.domain.user.User
import kotlinx.serialization.Serializable

@Serializable
data class CreatedUserResponse(
	val username: String,
	val newUser: User
)
