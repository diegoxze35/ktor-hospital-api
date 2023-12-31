package com.hospital.escom.adapter.persistence.repository.user

import com.hospital.escom.adapter.persistence.entity.PatientEntity
import com.hospital.escom.domain.CreatedUserResponse
import com.hospital.escom.domain.user.PatientUser
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PatientRepository : BaseUserRepository<PatientUser>() {
	override suspend fun addUser(user: PatientUser, password: String): CreatedUserResponse = newSuspendedTransaction {
		val (newUser, newUsername) = super.addUserEntity(user, password)
		val newPatient = PatientEntity.new {
			this.user = newUser
			personKey = user.personKey
		}.toDomain()
		return@newSuspendedTransaction CreatedUserResponse(username = newUsername, newUser = newPatient)
	}
}