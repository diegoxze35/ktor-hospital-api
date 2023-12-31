package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.ReceptionistTable
import com.hospital.escom.application.mappers.DomainWrapper
import com.hospital.escom.domain.user.ReceptionistUser
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ReceptionistEntity(id: EntityID<Int>) : IntEntity(id), DomainWrapper<ReceptionistUser> {
	companion object: IntEntityClass<ReceptionistEntity>(ReceptionistTable)
	var user by UserEntity referencedOn ReceptionistTable.id
	
	override fun toDomain(): ReceptionistUser {
		return ReceptionistUser(
			name = user.name,
			paternal = user.paternal,
			maternal = user.maternal,
			gender = enumValueOf(user.gender.genderType)
		)
	}
}