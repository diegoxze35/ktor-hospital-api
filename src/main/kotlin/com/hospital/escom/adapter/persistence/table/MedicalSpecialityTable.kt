package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.dao.id.IntIdTable

object MedicalSpecialityTable : IntIdTable(name = "Especialidad", columnName = "idEspecialidad") {
	val speciality = varchar(name = "nombre", length = 50)
	val citeAmount = double(name = "precioConsulta")
}