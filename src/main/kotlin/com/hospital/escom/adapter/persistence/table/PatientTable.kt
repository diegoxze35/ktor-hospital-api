package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.sql.Table

object PatientTable: Table(name = "Paciente") {
	val userId = reference(name = "idPaciente", UserTable.id)
	override val primaryKey: PrimaryKey
		get() = PrimaryKey(firstColumn = userId, name = "idPaciente")
	val curp = varchar(name = "curp", length = 18)
}