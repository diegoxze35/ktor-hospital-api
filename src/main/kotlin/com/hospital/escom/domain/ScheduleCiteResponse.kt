package com.hospital.escom.domain

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleCiteResponse(
	val citeId: Int,
	val specialityName: String,
	val dateTime: String,
	val roomNumber: Int
)
