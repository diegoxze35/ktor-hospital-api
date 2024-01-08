package com.hospital.escom.domain.medicalAppointment

import java.sql.Timestamp
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("ofUser")
sealed class MedicalAppointment {
	abstract val date: Timestamp
	abstract val roomNumber: Int
}