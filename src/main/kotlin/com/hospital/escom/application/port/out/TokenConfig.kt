package com.hospital.escom.application.port.out

data class TokenConfig(
	val issuer: String,
	val audience: String,
	val expiredAtInMillis: Long
)