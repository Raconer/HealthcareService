package com.health.care.health.controller.dto.request.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeDeserializer : JsonDeserializer<LocalDateTime>(){

    private val formats = listOf(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
    )

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDateTime {
        val text = p.text.trim()
        for (fmt in formats) {
            try {
                return LocalDateTime.parse(text, fmt)
            } catch (_: Exception) {}
        }
        throw IllegalArgumentException("Unsupported date format: $text")
    }
}