package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.UserTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
	companion object: IntEntityClass<UserEntity>(UserTable)
	
	var name by UserTable.name
	var paternal by UserTable.paternal
	var maternal by UserTable.maternal
	var username by UserTable.username
	var password by UserTable.password
	var role by UserRoleEntity referencedOn UserTable.userRol
	var gender by UserGenderEntity referencedOn UserTable.idGender
}