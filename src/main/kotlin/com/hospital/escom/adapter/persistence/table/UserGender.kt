package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.dao.id.IntIdTable


object UserGender : IntIdTable(name = "GeneroUsuario", columnName = "idGenero") {
	val gender = varchar(name = "genero", length = 50)
}