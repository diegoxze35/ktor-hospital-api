package com.hospital.escom.application.port.out.medicalinformation

import com.hospital.escom.domain.medicalAppointment.MedicalAppointment

interface MedicalInformationRepository {
	
	suspend fun allMedicalAppointments(userId: Int, statusId: Int): List<MedicalAppointment>
}