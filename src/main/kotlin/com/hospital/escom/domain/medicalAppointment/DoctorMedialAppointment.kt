package com.hospital.escom.domain.medicalAppointment

import java.sql.Timestamp
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("Doctor")
data class DoctorMedialAppointment(
	override val date: @Contextual Timestamp,
	override val roomNumber: Int,
	val patientFullName: String
) : MedicalAppointment()