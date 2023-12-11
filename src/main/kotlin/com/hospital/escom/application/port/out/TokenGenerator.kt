package com.hospital.escom.application.port.out

interface TokenGenerator {
	fun createToken(
		tokenConfig: TokenConfig,
		vararg claims: Pair<String, String>
	): String
}