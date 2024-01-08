package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.dao.id.IntIdTable

object ConsultingRoomTable: IntIdTable(name = "Consultorio", columnName = "idConsultorio") {
	val number = integer(name = "numero")
}