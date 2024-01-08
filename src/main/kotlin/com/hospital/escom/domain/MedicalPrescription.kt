package com.hospital.escom.domain

import java.sql.Timestamp
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class MedicalPrescription(
	val folio: Int,
	val date: @Contextual Timestamp,
	val patientFullName: String,
	val doctorFullName: String,
	val diagnostic: String,
	val medicamentName: String,
	val medicamentUnit: String,
	val duration: String,
	val period: String
)
