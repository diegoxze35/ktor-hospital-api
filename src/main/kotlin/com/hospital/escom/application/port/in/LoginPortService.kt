package com.hospital.escom.application.port.`in`

import com.hospital.escom.domain.AuthenticatedUser
import com.hospital.escom.domain.UserCredentials

interface LoginPortService {
	suspend fun login(credentials: UserCredentials): AuthenticatedUser?
}