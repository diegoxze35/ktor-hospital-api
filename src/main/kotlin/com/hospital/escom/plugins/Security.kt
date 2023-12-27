package com.hospital.escom.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.hospital.escom.adapter.persistence.entity.UserEntity
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import org.jetbrains.exposed.sql.transactions.transaction

private const val JWT_CONFIG = "jwt"
fun Application.configureSecurity() {
	
	val jwtConfig = environment.config.config(JWT_CONFIG)
	val jwtAudience = jwtConfig.property("audience").getString()
	val jwtIssuer = jwtConfig.property("issuer").getString()
	val jwtRealm = jwtConfig.property("realm").getString()
	val jwtSecret = System.getenv("jwt_secret")
	authentication {
		jwt {
			realm = jwtRealm
			verifier(
				JWT
					.require(Algorithm.HMAC256(jwtSecret))
					.withAudience(jwtAudience)
					.withIssuer(jwtIssuer)
					.withClaimPresence("role")
					.withClaimPresence("userId")
					.build()
			)
			
			validate { credential ->
				val existUser = credential["userId"]?.toInt()?.let {
					transaction {
						UserEntity.findById(it) !== null
					}
				} ?: return@validate null
				if (existUser && credential.payload.audience.contains(jwtAudience))
					JWTPrincipal(credential.payload)
				else null
			}
		}
	}
}
