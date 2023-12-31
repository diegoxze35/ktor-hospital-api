package com.hospital.escom.domain.user

import com.hospital.escom.domain.UserGender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Patient")
data class PatientUser(
	override val name: String,
	override val paternal: String,
	override val maternal: String,
	override val gender: UserGender,
	val personKey: String
) : User()

