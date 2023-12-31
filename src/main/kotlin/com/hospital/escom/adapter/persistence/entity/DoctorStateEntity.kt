package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.DoctorStateTable
import com.hospital.escom.adapter.persistence.table.DoctorTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class DoctorStateEntity(id: EntityID<Int>) : IntEntity(id) {
	companion object : IntEntityClass<DoctorStateEntity>(DoctorTable)
	
	var state by DoctorStateTable.description
}