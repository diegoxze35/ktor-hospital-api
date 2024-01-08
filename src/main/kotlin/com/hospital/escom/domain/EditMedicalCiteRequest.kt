package com.hospital.escom.domain

import kotlinx.serialization.Serializable

@Serializable
data class EditMedicalCiteRequest(
	val citeId: Int,
	val newDate: String,
	val newRoomNumber: Int
)
