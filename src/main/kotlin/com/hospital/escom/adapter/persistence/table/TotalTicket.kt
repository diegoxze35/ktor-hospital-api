package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object TotalTicket : IdTable<Int>() {
	override val id: Column<EntityID<Int>> = reference(name = "idPaciente", PatientTable.id)
	override val primaryKey: PrimaryKey = PrimaryKey(firstColumn = id, name = "idPaciente")
	val totalTicket  = double(name = "totTicket")
}