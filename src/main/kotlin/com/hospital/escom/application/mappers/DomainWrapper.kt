package com.hospital.escom.application.mappers

interface DomainWrapper<T> {
	fun toDomain(): T
}