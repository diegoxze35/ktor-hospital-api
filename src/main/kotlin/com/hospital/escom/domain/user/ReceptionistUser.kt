package com.hospital.escom.domain.user

import com.hospital.escom.domain.UserGender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Receptionist")
data class ReceptionistUser(
	override val name: String,
	override val paternal: String,
	override val maternal: String,
	override val gender: UserGender
) : User()
