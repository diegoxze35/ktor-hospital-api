package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.ReceptionistTable
import com.hospital.escom.application.mappers.DomainWrapper
import com.hospital.escom.domain.user.ReceptionistUser
import com.hospital.escom.domain.user.User
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ReceptionistEntity(id: EntityID<Int>) : IntEntity(id), DomainWrapper<User> {
	companion object: IntEntityClass<ReceptionistEntity>(ReceptionistTable)
	val user by UserEntity referencedOn ReceptionistTable.id
	
	override fun toDomain(): User {
		return ReceptionistUser(
			id = user.id.value,
			name = user.name,
			paternal = user.paternal,
			maternal = user.maternal,
			gender = enumValueOf(user.gender.genderType)
		)
	}
}