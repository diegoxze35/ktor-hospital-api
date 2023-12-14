package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.DoctorTable
import com.hospital.escom.application.mappers.DomainWrapper
import com.hospital.escom.domain.user.DoctorUser
import com.hospital.escom.domain.user.User
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class DoctorEntity(id: EntityID<Int>) : IntEntity(id), DomainWrapper<User> {
	companion object : IntEntityClass<DoctorEntity>(DoctorTable)
	val user by UserEntity referencedOn DoctorTable.id
	val speciality by DoctorSpecialityEntity referencedOn DoctorTable.speciality
	val license by DoctorTable.license
	
	override fun toDomain(): User {
		return DoctorUser(
			id = user.id.value,
			name = user.name,
			maternal = user.maternal,
			paternal = user.paternal,
			gender = enumValueOf(user.gender.genderType),
			speciality = speciality.specialityName,
			license = license
		)
	}
}