package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable(name = "Usuario", columnName = "idUsuario") {
	
	private const val LENGTH = 50
	
	val name = varchar(name = "nombre", length = LENGTH)
	val paternal = varchar(name = "paterno", length = LENGTH)
	val maternal = varchar(name = "materno", length = LENGTH)
	val username = varchar(name = "nombreUsuario", length = LENGTH)
	val password = varchar(name = "contraseña", length = LENGTH)
	val userRol = reference(name = "idRol", UserRoleTable)
	val idGender = reference(name = "idGenero", UserGender)
}