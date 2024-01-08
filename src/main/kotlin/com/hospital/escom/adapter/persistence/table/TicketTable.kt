package com.hospital.escom.adapter.persistence.table

import org.jetbrains.exposed.dao.id.IntIdTable

object TicketTable : IntIdTable(name = "Ticket", columnName = "idTicket") {
	val patientId = reference(name = "idPaciente", PatientTable.id)
	val concept = varchar(name = "concepto", length = 100)
	val amount = double(name = "costo")
}