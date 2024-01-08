package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.ConsultingRoomTable
import com.hospital.escom.adapter.persistence.table.MedicalCiteTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class MedicalCiteEntity(id: EntityID<Int>) : IntEntity(id) {
	
	companion object : IntEntityClass<MedicalCiteEntity>(MedicalCiteTable)
	val patients by PatientEntity referrersOn MedicalCiteTable.patientId
	val doctors by DoctorEntity referrersOn MedicalCiteTable.doctorId
	val roomId by ConsultingRoomEntity referrersOn ConsultingRoomTable.id
}