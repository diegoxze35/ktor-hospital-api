package com.hospital.escom.application.service.medicalinformation

import com.hospital.escom.application.port.out.medicalinformation.DoctorMedicalInformationRepository
import com.hospital.escom.domain.medicalAppointment.DoctorMedialAppointment

class DoctorMedicalInformationService(override val repository: DoctorMedicalInformationRepository) : BaseMedicalInformationService() {
	override suspend fun allMedicalAppointments(userId: Int, statusId: Int): List<DoctorMedialAppointment> {
		return repository.allMedicalAppointments(userId, statusId)
	}
}