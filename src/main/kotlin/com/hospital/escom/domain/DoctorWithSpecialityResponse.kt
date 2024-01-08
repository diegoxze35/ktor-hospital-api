package com.hospital.escom.domain

import kotlinx.serialization.Serializable

@Serializable
data class DoctorWithSpecialityResponse(
	val doctorId: Int,
	val fullName: String
)
