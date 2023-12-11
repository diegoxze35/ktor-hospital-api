package com.hospital.escom.utils

import com.auth0.jwt.JWTCreator

fun JWTCreator.Builder.withClaims(
	claims: Array<out Pair<String, String>>
): JWTCreator.Builder = apply {
	claims.forEach {
		withClaim(it.first, it.second)
	}
}
