package com.hospital.escom.adapter.persistence.dao

import com.hospital.escom.adapter.persistence.entity.DoctorEntity
import com.hospital.escom.adapter.persistence.entity.PatientEntity
import com.hospital.escom.adapter.persistence.entity.ReceptionistEntity
import com.hospital.escom.adapter.persistence.entity.UserEntity
import com.hospital.escom.application.port.out.LoadUserPort
import com.hospital.escom.application.port.out.domain.LoadResult
import com.hospital.escom.domain.UserCredentials
import com.hospital.escom.domain.UserRole
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.statements.StatementType
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class LoadUserPortImpl : LoadUserPort {
	private val columnUser = "nombreUsuario"
	private val passwordColum = "contraseña"
	private val resultIdColum = "idUsuario"
	private val resultPasswordColum = "correctPassword"
	override suspend fun loadUser(credentials: UserCredentials): LoadResult = newSuspendedTransaction(Dispatchers.IO) {
		val (username, password) = credentials
		val result = exec(
			stmt = "exec login_sp ?, ?",
			args = listOf(
				VarCharColumnType(collate = columnUser, colLength = 50) to username,
				VarCharColumnType(collate = passwordColum, colLength = 50) to password
			),
			explicitStatementType = StatementType.EXEC
		) {
			it.next()
			val id: Int = it.getInt(resultIdColum)
			val correctPassword: Int = it.getInt(resultPasswordColum)
			id to (correctPassword == 1)
		}
		val (userId, isCorrectPassword) = result ?: return@newSuspendedTransaction LoadResult.LoadNotFound
		return@newSuspendedTransaction if (userId > 0) {
			val role = enumValueOf<UserRole>(UserEntity[userId].role.roleType)
			val user = when (role) {
				UserRole.Patient -> PatientEntity[userId]
				UserRole.Doctor -> DoctorEntity[userId]
				UserRole.Receptionist -> ReceptionistEntity[userId]
			}.toDomain()
			if (isCorrectPassword)
				LoadResult.LoadWithCorrectPassword(user = user)
			else
				LoadResult.LoadWithIncorrectPassword(user = user)
		} else
			LoadResult.LoadNotFound
	}
}