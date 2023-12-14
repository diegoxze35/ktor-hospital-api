package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object ReceptionistTable : IdTable<Int>() {
	override val id: Column<EntityID<Int>> = reference(name = "idRecepcionista", UserTable.id)
	override val primaryKey: PrimaryKey = PrimaryKey(firstColumn = id)
}