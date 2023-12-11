package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.UserTable
import com.hospital.escom.application.mappers.DomainWrapper
import com.hospital.escom.domain.User
import com.hospital.escom.domain.UserGender
import com.hospital.escom.domain.UserRole
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Int>) : IntEntity(id), DomainWrapper<User> {
	companion object: IntEntityClass<UserEntity>(UserTable)
	
	var name by UserTable.name
	var paternal by UserTable.paternal
	var maternal by UserTable.maternal
	val username by UserTable.username
	var password by UserTable.password
	val role by UserRoleEntity referencedOn UserTable.userRol
	val gender by UserGenderEntity referencedOn UserTable.idGender
	
	override fun toDomain(): User = User(
		id = id.value,
		name = name,
		paternal = paternal,
		maternal = maternal,
		role = UserRole.valueOf(role.roleName),
		gender = UserGender.valueOf(gender.genderName)
	)
}