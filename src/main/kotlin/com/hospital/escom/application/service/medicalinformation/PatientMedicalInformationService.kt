package com.hospital.escom.application.service.medicalinformation

import com.hospital.escom.application.port.out.medicalinformation.MedicalInformationRepository
import com.hospital.escom.domain.medicalAppointment.MedicalAppointment

class PatientMedicalInformationService(override val repository: MedicalInformationRepository) :
	BaseMedicalInformationService() {
	
	override suspend fun allMedicalAppointments(userId: Int, statusId: Int): List<MedicalAppointment> {
		return repository.allMedicalAppointments(userId, statusId)
	}
	
}