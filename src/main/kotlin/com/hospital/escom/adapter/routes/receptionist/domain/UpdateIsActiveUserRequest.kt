package com.hospital.escom.adapter.routes.receptionist.domain

import com.hospital.escom.domain.UpdatableUser
import kotlinx.serialization.Serializable

@Serializable
data class UpdateIsActiveUserRequest(
	val userId: Int,
	val userType: UpdatableUser,
	val newIsActive: Boolean
)
