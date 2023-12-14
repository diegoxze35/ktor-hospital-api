package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.dao.id.IntIdTable

object DoctorSpecialityTable : IntIdTable(name = "Especialidad", columnName = "idEspecialidad") {
	val speciality = varchar(name = "nombre", length = 50)
}