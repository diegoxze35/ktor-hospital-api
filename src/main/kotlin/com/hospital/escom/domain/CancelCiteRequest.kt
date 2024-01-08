package com.hospital.escom.domain

import kotlinx.serialization.Serializable

@Serializable
data class CancelCiteRequest(
	val citeId: Int
)
