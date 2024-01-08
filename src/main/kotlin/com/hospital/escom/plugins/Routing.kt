package com.hospital.escom.plugins

import com.hospital.escom.adapter.routes.doctor.doctorRoutes
import com.hospital.escom.adapter.routes.login
import com.hospital.escom.adapter.routes.patient.patientRoutes
import com.hospital.escom.adapter.routes.receptionist.receptionistRoutes
import com.hospital.escom.adapter.routes.userpatientreceptionistcommon.patientReceptionistCommon
import com.hospital.escom.application.port.`in`.LoginPortService
import com.hospital.escom.application.port.`in`.medicalinformation.MedicalInformationService
import com.hospital.escom.application.port.`in`.user.UserService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
	routing {
		route("/api") {
			val loginService: LoginPortService by inject()
			login(loginService)
			authenticate {
				install(AuthenticationWithRolesPlugin)
				patientReceptionistCommon()
				val userService: UserService by inject()
				receptionistRoutes(userService)
				val patientService: MedicalInformationService by inject(named("PatientService"))
				patientRoutes(patientService)
				val doctorService: MedicalInformationService by inject(named("DoctorService"))
				doctorRoutes(doctorService)
			}
		}
	}
}
