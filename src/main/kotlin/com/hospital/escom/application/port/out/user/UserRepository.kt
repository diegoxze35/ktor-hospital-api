package com.hospital.escom.application.port.out.user

import com.hospital.escom.domain.CreatedUserResponse
import com.hospital.escom.domain.user.User

interface UserRepository<in U: User> {
	suspend fun addUser(user: U, password: String): CreatedUserResponse
}