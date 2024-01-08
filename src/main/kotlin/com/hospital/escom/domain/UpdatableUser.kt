package com.hospital.escom.domain

import kotlinx.serialization.Serializable

@Serializable
enum class UpdatableUser {
	Patient, Doctor
}