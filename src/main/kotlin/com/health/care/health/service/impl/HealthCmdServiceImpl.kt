package com.health.care.health.service.impl

import com.health.care.health.controller.dto.request.HealthSaveRequest
import com.health.care.health.mapper.HealthMapper
import com.health.care.health.service.HealthCmdService
import org.springframework.stereotype.Service

@Service
class HealthCmdServiceImpl(
    private val healthMapper: HealthMapper
) : HealthCmdService {
    override fun save(healthSaveRequest: HealthSaveRequest) {
        val data = this.healthMapper.toEntity(healthSaveRequest)
        println(data)
    }
}