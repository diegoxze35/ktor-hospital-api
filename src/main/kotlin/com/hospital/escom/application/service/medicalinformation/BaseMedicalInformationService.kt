package com.hospital.escom.application.service.medicalinformation

import com.hospital.escom.application.port.`in`.medicalinformation.MedicalInformationService
import com.hospital.escom.application.port.out.medicalinformation.MedicalInformationRepository

abstract class BaseMedicalInformationService : MedicalInformationService {
	protected abstract val repository: MedicalInformationRepository
}