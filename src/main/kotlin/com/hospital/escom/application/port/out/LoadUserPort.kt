package com.hospital.escom.application.port.out

import com.hospital.escom.domain.user.User
import com.hospital.escom.domain.UserCredentials

interface LoadUserPort {
	suspend fun loadUser(credentials: UserCredentials): User?
}