package com.hospital.escom.adapter.persistence.dao

import com.hospital.escom.adapter.persistence.entity.UserEntity
import com.hospital.escom.application.port.out.LoadUserPort
import com.hospital.escom.domain.User
import com.hospital.escom.domain.UserCredentials
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.statements.StatementType
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class LoadUserPortImpl : LoadUserPort {
	private val columnUser = "nombreUsuario"
	private val passwordColum = "contraseña"
	private val resultColum = "idUsuario"
	override suspend fun loadUser(credentials: UserCredentials): User? = newSuspendedTransaction(Dispatchers.IO) {
		val (username, password) = credentials
		val userId = exec(
			stmt = "exec login ?, ?",
			args = listOf(
				VarCharColumnType(collate = columnUser, colLength = 50) to username,
				VarCharColumnType(collate = passwordColum, colLength = 50) to password
			),
			explicitStatementType = StatementType.EXEC
		) {
			it.next()
			it.getInt(resultColum)
		}
		return@newSuspendedTransaction if (userId != null && userId > 0) {
			UserEntity.findById(userId)!!.toDomain()
		} else null
	}
}