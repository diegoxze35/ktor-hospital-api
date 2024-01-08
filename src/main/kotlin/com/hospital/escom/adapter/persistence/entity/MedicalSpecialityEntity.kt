package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.MedicalSpecialityTable
import com.hospital.escom.application.mappers.DomainWrapper
import com.hospital.escom.domain.MedicalSpeciality
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class MedicalSpecialityEntity(id: EntityID<Int>) : IntEntity(id), DomainWrapper<MedicalSpeciality> {
	override fun toDomain(): MedicalSpeciality = MedicalSpeciality(
		name = specialityName,
		citeAmount = citeAmount
	)
	
	companion object : IntEntityClass<MedicalSpecialityEntity>(MedicalSpecialityTable)
	var specialityName by MedicalSpecialityTable.speciality
	var citeAmount by MedicalSpecialityTable.citeAmount
}