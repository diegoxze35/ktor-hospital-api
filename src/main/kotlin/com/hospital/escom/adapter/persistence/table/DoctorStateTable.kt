package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.dao.id.IntIdTable

object DoctorStateTable : IntIdTable() {
	val description = varchar(name = "descripcion", length = 50)
}