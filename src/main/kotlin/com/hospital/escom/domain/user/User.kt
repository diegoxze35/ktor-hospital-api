package com.hospital.escom.domain.user

import com.hospital.escom.domain.UserGender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class User {
	@SerialName("userId")
	abstract val id: Int
	@SerialName("userName")
	abstract val name: String
	@SerialName("userPaternal")
	abstract val paternal: String
	@SerialName("serialMaternal")
	abstract val maternal: String
	@SerialName("userGender")
	abstract val gender: UserGender
}
