package com.hospital.escom.application.service

import com.hospital.escom.application.port.`in`.user.UserService
import com.hospital.escom.application.port.out.user.UserRepository
import com.hospital.escom.domain.CreatedUserResponse
import com.hospital.escom.domain.user.User

class UserServiceImpl(private val getRepository: (User) -> UserRepository<User>) : UserService {
	
	private lateinit var repository: UserRepository<User>
	
	override suspend fun addUser(user: User, password: String): CreatedUserResponse = user.run {
		repository = getRepository(this)
		return@run repository.addUser(this, password)
	}
}