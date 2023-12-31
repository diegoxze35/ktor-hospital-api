package com.hospital.escom.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.hospital.escom.adapter.persistence.repository.LoadUserPortImpl
import com.hospital.escom.adapter.persistence.repository.user.DoctorRepository
import com.hospital.escom.adapter.persistence.repository.user.PatientRepository
import com.hospital.escom.adapter.persistence.repository.user.ReceptionistRepository
import com.hospital.escom.application.port.`in`.LoginPortService
import com.hospital.escom.application.port.`in`.user.UserService
import com.hospital.escom.application.port.out.LoadUserPort
import com.hospital.escom.application.port.out.TokenConfig
import com.hospital.escom.application.port.out.TokenGenerator
import com.hospital.escom.application.port.out.user.UserRepository
import com.hospital.escom.application.service.LoginUserService
import com.hospital.escom.application.service.UserServiceImpl
import com.hospital.escom.domain.user.DoctorUser
import com.hospital.escom.domain.user.PatientUser
import com.hospital.escom.domain.user.ReceptionistUser
import com.hospital.escom.domain.user.User
import com.hospital.escom.utils.withClaims
import io.ktor.server.application.Application
import io.ktor.server.application.install
import java.util.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureDependencyInjection() {
	install(Koin) {
		modules(
			loginModule,
			tokenConfigurationModule(this@configureDependencyInjection),
			userRepositoryModule
		)
	}
}

fun tokenConfigurationModule(application: Application): Module = module {
	single {
		val jwtConfig = application.environment.config.config("jwt")
		return@single TokenConfig(
			audience = jwtConfig.property("audience").getString(),
			issuer = jwtConfig.property("issuer").getString(),
			expiredAtInMillis = 86_400_000L
		)
	}
}

val loginModule = module {
	single<LoadUserPort> { return@single LoadUserPortImpl() }
	single<TokenGenerator> {
		return@single object : TokenGenerator {
			override fun createToken(
				tokenConfig: TokenConfig,
				vararg claims: Pair<String, String>
			): String = JWT.create()
				.withAudience(tokenConfig.audience)
				.withIssuer(tokenConfig.issuer)
				.withExpiresAt(Date(System.currentTimeMillis() + tokenConfig.expiredAtInMillis))
				.withClaims(claims)
				.sign(Algorithm.HMAC256(System.getenv("jwt_secret")))
		}
	}
	
	single<LoginPortService> {
		LoginUserService(
			loadUserPort = get(),
			tokenGenerator = get(),
			tokenConfig = get()
		)
	}
}

val userRepositoryModule = module {
	single<UserService> {
		UserServiceImpl {
			when (it) {
				is PatientUser -> PatientRepository()
				is DoctorUser -> DoctorRepository()
				is ReceptionistUser -> ReceptionistRepository()
			} as UserRepository<User>
		}
	}
}
