package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.UserGender
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserGenderEntity(id: EntityID<Int>) : IntEntity(id) {
	companion object : IntEntityClass<UserGenderEntity>(UserGender)
	val gender by UserGender.gender
}