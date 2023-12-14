package com.hospital.escom.domain.user

import com.hospital.escom.domain.UserGender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatientUser(
	//@SerialName("patientId")
	override val id: Int,
	//@SerialName("patientName")
	override val name: String,
	//@SerialName("paternalName")
	override val paternal: String,
	//@SerialName("patientMaternal")
	override val maternal: String,
	//@SerialName("patientGender")
	override val gender: UserGender,
	val personKey: String
) : User()

