package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.UserRoleTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserRoleEntity(id: EntityID<Int>) : IntEntity(id) {
	companion object : IntEntityClass<UserRoleEntity>(UserRoleTable)
	val roleType by UserRoleTable.rolName
}