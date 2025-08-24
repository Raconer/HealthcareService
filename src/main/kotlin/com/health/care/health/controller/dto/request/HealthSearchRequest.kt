package com.health.care.health.controller.dto.request

import com.health.care.health.domain.enums.SearchType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import java.time.LocalDate

@Schema(description = "검색 요청")
data class HealthSearchRequest(
    @field:Schema(description = "페이지 번호 (1부터 시작)", example = "1")
    @field:Min(value = 1, message = "page는 1 이상이어야 합니다.")
    var page: Int = 1,
    @field:Schema(description = "페이지당 데이터 수", example = "10")
    @field:Min(value = 1, message = "size는 1 이상이어야 합니다.")
    var size: Int = 10,
    @field:Schema(description = "일별 월별 조회", example = "MONTH, DAY")
    var searchType: SearchType = SearchType.MONTH,
    @field:Pattern(
        regexp = "\\d{4}-\\d{2}-\\d{2}",
        message = "from 날짜는 yyyy-MM-dd 형식이어야 합니다."
    )
    @field:NotNull
    val from: LocalDate = LocalDate.now(),
    @field:Pattern(
        regexp = "\\d{4}-\\d{2}-\\d{2}",
        message = "to 날짜는 yyyy-MM-dd 형식이어야 합니다."
    )
    @field:NotNull
    val to: LocalDate = LocalDate.now().minusMonths(1),
){
    @AssertTrue(message = "from은 to보다 이후일 수 없습니다.")
    fun isValidPeriod(): Boolean = !from.isAfter(to)
}