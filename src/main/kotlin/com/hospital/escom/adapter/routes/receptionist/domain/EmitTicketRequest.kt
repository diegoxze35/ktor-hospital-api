package com.hospital.escom.adapter.routes.receptionist.domain

import kotlinx.serialization.Serializable

@Serializable
data class EmitTicketRequest(val patientId: Int)
