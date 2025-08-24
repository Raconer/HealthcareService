package com.health.care.health.domain.enums

enum class SearchType(
    val unit : String,
    val desc : String
) {
    MONTH("month", "월별 조회"),
    DAY("day", "일별 조회")
}