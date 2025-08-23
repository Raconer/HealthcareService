package com.health.care.common.dto.event

import java.time.LocalDateTime

data class ParseError(
    val path: String,
    val field: String,
    val rejectedValue: String?,
    val message: String,
    val occurredAt: LocalDateTime = LocalDateTime.now()
)
