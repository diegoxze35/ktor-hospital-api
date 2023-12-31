package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.PatientTable
import com.hospital.escom.application.mappers.DomainWrapper
import com.hospital.escom.domain.user.PatientUser
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PatientEntity(userId: EntityID<Int>) : IntEntity(userId), DomainWrapper<PatientUser> {
	companion object : IntEntityClass<PatientEntity>(PatientTable)
	var user by UserEntity referencedOn PatientTable.id
	var personKey by PatientTable.personKey
	
	override fun toDomain(): PatientUser = PatientUser(
		name = user.name,
		maternal = user.maternal,
		paternal = user.paternal,
		gender = enumValueOf(user.gender.genderType),
		personKey = personKey
	)
}