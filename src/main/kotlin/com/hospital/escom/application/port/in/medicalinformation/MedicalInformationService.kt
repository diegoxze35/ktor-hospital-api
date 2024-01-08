package com.hospital.escom.application.port.`in`.medicalinformation

import com.hospital.escom.domain.medicalAppointment.MedicalAppointment

interface MedicalInformationService {
	suspend fun allMedicalAppointments(userId: Int, statusId: Int): List<MedicalAppointment>
}