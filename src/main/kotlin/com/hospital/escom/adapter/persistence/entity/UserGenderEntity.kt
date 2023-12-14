package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.UserGenderTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserGenderEntity(id: EntityID<Int>) : IntEntity(id) {
	companion object : IntEntityClass<UserGenderEntity>(UserGenderTable)
	val genderType by UserGenderTable.gender
}