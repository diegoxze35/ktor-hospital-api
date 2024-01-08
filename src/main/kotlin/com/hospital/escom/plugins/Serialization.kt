package com.hospital.escom.plugins

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import java.sql.Timestamp
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

@OptIn(ExperimentalSerializationApi::class)
fun Application.configureSerialization() {
	install(ContentNegotiation) {
		json(
			Json {
				removeIgnoredType(String::class)
				encodeDefaults = true
				explicitNulls = true
				serializersModule = SerializersModule {
					contextual(
						Timestamp::class,
						object : KSerializer<Timestamp> {
							override val descriptor: SerialDescriptor
								get() = PrimitiveSerialDescriptor(serialName = "timespan", PrimitiveKind.LONG)
							
							override fun deserialize(decoder: Decoder): Timestamp {
								return Timestamp(decoder.decodeLong() * 1_000L)
							}
							
							override fun serialize(encoder: Encoder, value: Timestamp) {
								encoder.encodeLong(value.time / 1_000L)
							}
							
						}
					)
				}
			}
		)
	}
}
