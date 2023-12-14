package com.hospital.escom.domain.user

import com.hospital.escom.domain.UserGender
import com.hospital.escom.domain.UserRole
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/*class UserSerializer(
	override val descriptor: SerialDescriptor
) : KSerializer<User> {
	
	private val userGenderDescriptor = UserGender.serializer().descriptor
	private val userGenderEntries = UserGender.entries
	private val userRoleEntries = UserRole.entries
	private val userRoleDescriptor = UserRole.serializer().descriptor
	
	override fun deserialize(decoder: Decoder): User {
		val id = decoder.decodeInt()
		val name = decoder.decodeString()
		val paternal = decoder.decodeString()
		val maternal = decoder.decodeString()
		val gender = userGenderEntries[decoder.decodeEnum(userGenderDescriptor)]
		return when (userRoleEntries[decoder.decodeEnum(userRoleDescriptor)]) {
			UserRole.Patient -> PatientUser(
				id = id,
				name = name,
				paternal = paternal,
				maternal = maternal,
				gender = gender,
				personKey = decoder.decodeString()
			)
			UserRole.Doctor -> DoctorUser(
				id = id,
				name = name,
				paternal = paternal,
				maternal = maternal,
				gender = gender,
				speciality = decoder.decodeString(),
				license = decoder.decodeInt()
			)
			UserRole.Receptionist -> ReceptionistUser(
				id = id,
				name = name,
				paternal = paternal,
				maternal = maternal,
				gender = gender
			)
		}
	}
	
	override fun serialize(encoder: Encoder, value: User) = encoder.run {
		encodeInt(value.id)
		encodeString(value.name)
		encodeString(value.paternal)
		encodeString(value.maternal)
		encodeEnum(UserGender.serializer().descriptor, value.gender.ordinal)
		when (value) {
			is PatientUser -> {
				encodeEnum(userRoleDescriptor, UserRole.Patient.ordinal)
				encodeString(value.personKey)
			}
			is DoctorUser -> {
				encodeEnum(userRoleDescriptor, UserRole.Doctor.ordinal)
				encodeString(value.speciality)
				encodeInt(value.license)
			}
			is ReceptionistUser -> encodeEnum(userRoleDescriptor, UserRole.Receptionist.ordinal)
		}
	}
}*/
