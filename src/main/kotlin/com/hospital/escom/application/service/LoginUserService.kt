package com.hospital.escom.application.service

import com.hospital.escom.application.port.`in`.LoginPortService
import com.hospital.escom.application.port.out.LoadUserPort
import com.hospital.escom.application.port.out.TokenConfig
import com.hospital.escom.application.port.out.TokenGenerator
import com.hospital.escom.application.port.out.domain.LoadResult
import com.hospital.escom.domain.LoginResult
import com.hospital.escom.domain.UserCredentials
import com.hospital.escom.domain.user.DoctorUser
import com.hospital.escom.domain.user.PatientUser
import com.hospital.escom.domain.user.ReceptionistUser

class LoginUserService(
	private val loadUserPort: LoadUserPort,
	private val tokenGenerator: TokenGenerator,
	private val tokenConfig: TokenConfig
) : LoginPortService {
	override suspend fun login(credentials: UserCredentials): LoginResult {
		val result = loadUserPort.loadUser(credentials)
		var token: String? = null
		if (result is LoadResult.LoadWithCorrectPassword) {
			val roleClaim = when (result.user) {
				is PatientUser -> "Patient"
				is DoctorUser -> "Doctor"
				is ReceptionistUser -> "Receptionist"
			}
			token = tokenGenerator.createToken(
				tokenConfig,
				"role" to roleClaim, "userId" to "${result.userId}"
			)
		}
		return LoginResult(token = token, user = result.user)
	}
}