package com.hospital.escom.application.port.out.domain

import com.hospital.escom.domain.user.User

sealed class LoadResult(open val user: User?) {
	data class LoadWithCorrectPassword(override val user: User, val userId: Int) : LoadResult(user)
	data class LoadWithIncorrectPassword(override val user: User) : LoadResult(user)
	data object LoadNotFound : LoadResult(null)
}