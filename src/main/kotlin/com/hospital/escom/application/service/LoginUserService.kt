package com.hospital.escom.application.service

import com.hospital.escom.application.port.`in`.LoginPortService
import com.hospital.escom.application.port.out.LoadUserPort
import com.hospital.escom.application.port.out.TokenConfig
import com.hospital.escom.application.port.out.TokenGenerator
import com.hospital.escom.domain.AuthenticatedUser
import com.hospital.escom.domain.UserCredentials

class LoginUserService(
	private val loadUserPort: LoadUserPort,
	private val tokenGenerator: TokenGenerator,
	private val tokenConfig: TokenConfig
) : LoginPortService {
	override suspend fun login(credentials: UserCredentials): AuthenticatedUser? {
		val user = loadUserPort.loadUser(credentials) ?: return null
		val token = tokenGenerator.createToken(tokenConfig, claims = arrayOf("role" to user.role.name))
		return AuthenticatedUser(token = token, user = user)
	}
}