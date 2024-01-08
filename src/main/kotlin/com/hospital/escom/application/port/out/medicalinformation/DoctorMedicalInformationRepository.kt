package com.hospital.escom.application.port.out.medicalinformation

import com.hospital.escom.domain.medicalAppointment.DoctorMedialAppointment

interface DoctorMedicalInformationRepository : MedicalInformationRepository {
	override suspend fun allMedicalAppointments(userId: Int, statusId: Int): List<DoctorMedialAppointment>
}