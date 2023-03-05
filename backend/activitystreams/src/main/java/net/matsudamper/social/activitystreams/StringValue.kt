package net.matsudamper.social.activitystreams

import kotlin.reflect.jvm.jvmName
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder

@Serializable(with = StringSerializer::class)
interface StringValue {
    val value: String
}

object StringSerializer : KSerializer<StringValue> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(StringSerializer::class.jvmName, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): StringValue = object : StringValue {
        override val value: String = decoder.decodeString()
    }

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: StringValue) {
        encoder.encodeString(value.value)
    }
}