package com.hospital.escom.application.port.`in`.user

import com.hospital.escom.domain.CreatedUserResponse
import com.hospital.escom.domain.user.User

interface UserService {
	suspend fun addUser(user: User, password: String): CreatedUserResponse
}