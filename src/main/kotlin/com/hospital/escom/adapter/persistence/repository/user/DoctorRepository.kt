package com.hospital.escom.adapter.persistence.repository.user

import com.hospital.escom.adapter.persistence.entity.DoctorEntity
import com.hospital.escom.adapter.persistence.entity.DoctorSpecialityEntity
import com.hospital.escom.adapter.persistence.entity.DoctorStateEntity
import com.hospital.escom.domain.CreatedUserResponse
import com.hospital.escom.domain.user.DoctorUser
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DoctorRepository : BaseUserRepository<DoctorUser>() {
	
	override suspend fun addUser(user: DoctorUser, password: String): CreatedUserResponse = newSuspendedTransaction {
		val (newUser, newUsername) = super.addUserEntity(user, password)
		val newDoctor = DoctorEntity.new {
			this.user = newUser
			speciality = DoctorSpecialityEntity.new {
				specialityName = user.speciality
				state = DoctorStateEntity[1]
			}
			license = user.license
		}.toDomain()
		return@newSuspendedTransaction CreatedUserResponse(username = newUsername, newUser = newDoctor)
	}
}