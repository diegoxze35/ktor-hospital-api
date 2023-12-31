package com.hospital.escom.adapter.persistence.repository.user

import com.hospital.escom.adapter.persistence.entity.ReceptionistEntity
import com.hospital.escom.domain.CreatedUserResponse
import com.hospital.escom.domain.user.ReceptionistUser
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ReceptionistRepository : BaseUserRepository<ReceptionistUser>() {
	override suspend fun addUser(user: ReceptionistUser, password: String): CreatedUserResponse = newSuspendedTransaction {
		val (newUser, newUsername) = super.addUserEntity(user, password)
		val newReceptionist = ReceptionistEntity.new {
			this.user = newUser
		}.toDomain()
		return@newSuspendedTransaction CreatedUserResponse(username = newUsername, newUser =  newReceptionist)
	}
}