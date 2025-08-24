package com.health.care.health.service

import com.health.care.common.dto.page.PageResponse
import com.health.care.health.controller.dto.request.HealthSearchRequest
import com.health.care.health.controller.dto.response.HealthDataResponse

interface HealthReadService {
    fun execute(healthSearchRequest: HealthSearchRequest): PageResponse<HealthDataResponse>
}