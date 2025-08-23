package com.health.care.health.service

import com.health.care.health.controller.dto.request.HealthSaveRequest

interface HealthCmdService {
    fun save(healthSaveRequest: HealthSaveRequest)
}