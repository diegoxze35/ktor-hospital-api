package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object DoctorTable : IdTable<Int>(name = "Doctor") {
	override val id: Column<EntityID<Int>> = reference(name = "idDoctor", UserTable)
	override val primaryKey: PrimaryKey = PrimaryKey(firstColumn = id, name = "idDoctor")
	val speciality = reference(name = "idEspecialidad", MedicalSpecialityTable)
	val license = integer(name = "cedula")
	val isActive = bool(name = "activo")
}