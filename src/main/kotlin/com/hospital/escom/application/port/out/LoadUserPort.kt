package com.hospital.escom.application.port.out

import com.hospital.escom.application.port.out.domain.LoadResult
import com.hospital.escom.domain.UserCredentials

interface LoadUserPort {
	suspend fun loadUser(credentials: UserCredentials): LoadResult
}