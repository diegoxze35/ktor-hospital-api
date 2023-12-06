package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.dao.id.IntIdTable

object UserRoleTable : IntIdTable(name = "RolDeUsuario", columnName = "idRol") {
	val rolName = varchar(name = "nombreRol", length = 50)
}