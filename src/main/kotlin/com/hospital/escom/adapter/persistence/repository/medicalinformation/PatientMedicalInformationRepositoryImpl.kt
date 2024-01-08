package com.hospital.escom.adapter.persistence.repository.medicalinformation

import com.hospital.escom.application.port.out.medicalinformation.PatientMedicalInformationRepository
import com.hospital.escom.domain.medicalAppointment.PatientMedicalAppointment
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PatientMedicalInformationRepositoryImpl : PatientMedicalInformationRepository {
	override suspend fun allMedicalAppointments(userId: Int, statusId: Int): List<PatientMedicalAppointment> {
		return newSuspendedTransaction {
			exec(
				stmt = "exec citas_paciente_sp ?, ?",
				args = listOf(
					IntegerColumnType() to userId,
					IntegerColumnType() to statusId
				)
			) {
				return@exec buildList {
					while (it.next()) {
						add(
							PatientMedicalAppointment(
								date = it.getTimestamp("Fecha"),
								roomNumber = it.getInt("Consultorio"),
								doctorFullName = it.getString("Nombre_Doctor"),
								doctorId = it.getInt("idDoctor"),
								citeId = it.getInt("idCita"),
								speciality = it.getString("Especialidad")
							)
						)
					}
				}
			}.orEmpty()
		}
	}
}