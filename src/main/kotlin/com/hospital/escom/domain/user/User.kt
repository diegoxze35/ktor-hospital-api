package com.hospital.escom.domain.user

import com.hospital.escom.domain.UserGender
import kotlinx.serialization.Serializable

@Serializable
sealed class User {
	abstract val name: String
	abstract val paternal: String
	abstract val maternal: String
	abstract val gender: UserGender
}
