package com.hospital.escom.application.mappers

interface DomainWrapper<out T> {
	fun toDomain(): T
}