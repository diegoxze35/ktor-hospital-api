package com.hospital.escom.application.port.out.medicalinformation

import com.hospital.escom.domain.medicalAppointment.PatientMedicalAppointment

interface PatientMedicalInformationRepository: MedicalInformationRepository {
	override suspend fun allMedicalAppointments(userId: Int, statusId: Int): List<PatientMedicalAppointment>
}