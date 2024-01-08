package com.hospital.escom.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
	val id: Int,
	val userType: UpdatableUser,
	val fullName: String,
	val isActive: Boolean
)
