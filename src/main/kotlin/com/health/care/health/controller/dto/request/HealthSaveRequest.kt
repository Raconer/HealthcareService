package com.health.care.health.controller.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.health.care.health.controller.dto.request.deserializer.DateTimeDeserializer
import com.health.care.health.controller.dto.request.deserializer.EntryDeserializer
import com.health.care.health.domain.enums.HealthInfoType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.time.OffsetDateTime

data class HealthSaveRequest(
    @field:NotNull
    val recordkey: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss Z")
    val lastUpdate: OffsetDateTime?,
    @field:NotBlank
    val type: HealthInfoType,
    @field:NotNull
    val data: Data,
) {
    data class Data(
        val memo: String?,
        @field:NotEmpty
        var entries: List<Entry?>,
        val source: Source?,
    )

    @JsonDeserialize(using = EntryDeserializer::class)
    data class Entry(
        var period: Period,
        val steps: Int? = null,
        var calories: Quantity? = null,
        var distance: Quantity? = null,
    )

    data class Period(
        @JsonDeserialize(using = DateTimeDeserializer::class)
        val from: LocalDateTime,
        @JsonDeserialize(using = DateTimeDeserializer::class)
        val to: LocalDateTime,
    )

    data class Quantity(
        @field:NotBlank
        val unit: String,
        val value: Float,
    )

    data class Source(
        val mode: Int?,
        val product: Product?,
        val name: String?,
        val type: String?,
    )

    data class Product(
        val name: String?,
        val vender: String?,
    )
}
