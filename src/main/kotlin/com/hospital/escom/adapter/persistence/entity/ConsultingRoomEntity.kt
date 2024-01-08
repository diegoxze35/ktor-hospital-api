package com.hospital.escom.adapter.persistence.entity

import com.hospital.escom.adapter.persistence.table.ConsultingRoomTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ConsultingRoomEntity(id: EntityID<Int>) : IntEntity(id) {
	companion object: IntEntityClass<ConsultingRoomEntity>(ConsultingRoomTable)
	
	val roomNumber by ConsultingRoomTable.number
}