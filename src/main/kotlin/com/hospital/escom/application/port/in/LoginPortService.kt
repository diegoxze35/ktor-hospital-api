package com.hospital.escom.application.port.`in`

import com.hospital.escom.domain.LoginResult
import com.hospital.escom.domain.UserCredentials

interface LoginPortService {
	suspend fun login(credentials: UserCredentials): LoginResult
}