package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.DoctorSpecialityTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class DoctorSpecialityEntity(id: EntityID<Int>) : IntEntity(id) {
	companion object : IntEntityClass<DoctorSpecialityEntity>(DoctorSpecialityTable)
	val specialityName by DoctorSpecialityTable.speciality
}