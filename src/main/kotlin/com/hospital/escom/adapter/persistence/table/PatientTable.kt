package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object PatientTable: IdTable<Int>(name = "Paciente") {
	override val id: Column<EntityID<Int>> = reference(name = "idPaciente", UserTable.id)
	override val primaryKey: PrimaryKey = PrimaryKey(firstColumn = id, name = "idPaciente")
	val personKey = varchar(name = "curp", length = 18)
	val isActive = bool(name = "activo")
}