package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

object MedicalCiteTable: IntIdTable(name = "CitaMedica", columnName = "idCita") {
	var patientId = reference(name = "idPaciente", refColumn = PatientTable.id)
	var doctorId = reference(name = "idDoctor", refColumn = DoctorTable.id)
	var datetime = datetime(name = "Fecha")
	var roomId = reference(name = "idConsultorio", refColumn = ConsultingRoomTable.id)
	val status = integer(name = "idEstatus")
	var isCanceled = bool(name = "cancelada")
}