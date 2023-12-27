package com.hospital.escom.domain.user

import com.hospital.escom.domain.UserGender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Doctor")
data class DoctorUser(
	override val id: Int,
	override val name: String,
	override val paternal: String,
	override val maternal: String,
	override val gender: UserGender,
	val speciality: String,
	val license: Int
) : User()
