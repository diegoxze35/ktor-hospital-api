package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.DoctorTable
import com.hospital.escom.application.mappers.DomainWrapper
import com.hospital.escom.domain.user.DoctorUser
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class DoctorEntity(id: EntityID<Int>) : IntEntity(id), DomainWrapper<DoctorUser> {
	companion object : IntEntityClass<DoctorEntity>(DoctorTable)
	var user by UserEntity referencedOn DoctorTable.id
	var speciality by DoctorSpecialityEntity referencedOn DoctorTable.speciality
	var license by DoctorTable.license
	var state by DoctorStateEntity referencedOn DoctorTable.state
	
	override fun toDomain(): DoctorUser {
		return DoctorUser(
			name = user.name,
			maternal = user.maternal,
			paternal = user.paternal,
			gender = enumValueOf(user.gender.genderType),
			speciality = speciality.specialityName,
			license = license
		)
	}
}