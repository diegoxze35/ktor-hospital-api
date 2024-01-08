package com.hospital.escom.domain.medicalAppointment

import java.sql.Timestamp
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Patient")
data class PatientMedicalAppointment(
	override val date: @Contextual Timestamp,
	override val roomNumber: Int,
	val doctorFullName: String,
	val doctorId: Int,
	val citeId: Int,
	val speciality: String
) : MedicalAppointment()
