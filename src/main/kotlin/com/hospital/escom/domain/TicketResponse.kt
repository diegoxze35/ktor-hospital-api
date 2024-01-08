package com.hospital.escom.domain

import kotlinx.serialization.Serializable

@Serializable
data class TicketResponse(
	val patientId: Int,
	val patientFullName: String,
	val tickets: List<Ticket>,
	val total: Double
)