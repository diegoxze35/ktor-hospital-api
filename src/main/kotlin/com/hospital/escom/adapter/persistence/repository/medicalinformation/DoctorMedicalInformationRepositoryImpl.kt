package com.hospital.escom.adapter.persistence.repository.medicalinformation

import com.hospital.escom.application.port.out.medicalinformation.DoctorMedicalInformationRepository
import com.hospital.escom.domain.medicalAppointment.DoctorMedialAppointment
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.statements.StatementType
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DoctorMedicalInformationRepositoryImpl : DoctorMedicalInformationRepository {
	override suspend fun allMedicalAppointments(userId: Int, statusId: Int): List<DoctorMedialAppointment> {
		return newSuspendedTransaction {
			return@newSuspendedTransaction exec(
				stmt = "exec citas_medico_sp ?, ?",
				args = listOf(
					IntegerColumnType() to userId,
					IntegerColumnType() to statusId
				),
				explicitStatementType = StatementType.EXEC
			) {
				buildList {
					while (it.next()) {
						add(
							DoctorMedialAppointment(
								patientFullName = it.getString(1),
								date = it.getTimestamp(2),
								roomNumber = it.getInt(3)
							)
						)
					}
				}
			}
		}.orEmpty()
	}
}