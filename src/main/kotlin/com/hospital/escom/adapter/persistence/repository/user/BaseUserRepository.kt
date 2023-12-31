package com.hospital.escom.adapter.persistence.repository.user

import com.hospital.escom.adapter.persistence.entity.UserEntity
import com.hospital.escom.adapter.persistence.entity.UserGenderEntity
import com.hospital.escom.adapter.persistence.entity.UserRoleEntity
import com.hospital.escom.application.port.out.user.UserRepository
import com.hospital.escom.domain.UserGender
import com.hospital.escom.domain.user.DoctorUser
import com.hospital.escom.domain.user.PatientUser
import com.hospital.escom.domain.user.ReceptionistUser
import com.hospital.escom.domain.user.User
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.statements.StatementType
import org.jetbrains.exposed.sql.transactions.transaction

abstract class BaseUserRepository<in U: User> : UserRepository<U> {
	
	protected fun addUserEntity(user: User, password: String): Pair<UserEntity, String> {
		return transaction {
			val firstLetterUserRole = user::class.java.simpleName[0].toString()
			val username = exec(
				stmt = "declare @nombre varchar(50);" +
						"exec @nombre = generarNombreUsuario ?, ?, ?, ?;" +
						"select @nombre as username;",
				args = listOf(
					VarCharColumnType(collate = "nombreUsuario", colLength = 50) to user.name,
					VarCharColumnType(collate = "nombreRol", colLength = 1) to firstLetterUserRole,
					VarCharColumnType(collate = "paterno", colLength = 50) to user.paternal,
					VarCharColumnType(collate = "materno", colLength = 50) to user.maternal
				),
				explicitStatementType = StatementType.SELECT
			) {
				it.next()
				it.getString("username")
			}!!
			UserEntity.new {
				name = user.name
				paternal = user.paternal
				maternal = user.maternal
				this.password = password
				this.username = username
				val idGender = when(user.gender) {
					UserGender.Female -> 0
					UserGender.Male -> 1
				}
				val idRol = when (user) {
					is PatientUser -> 1
					is DoctorUser -> 2
					is ReceptionistUser -> 3
				}
				gender = UserGenderEntity[idGender]
				role = UserRoleEntity[idRol]
			} to username
		}
	}
}