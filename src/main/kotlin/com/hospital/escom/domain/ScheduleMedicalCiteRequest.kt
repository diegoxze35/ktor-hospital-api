package com.hospital.escom.domain

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleMedicalCiteRequest(
	val doctorId: Int,
	val date: String,
	val roomNumber: Int
)