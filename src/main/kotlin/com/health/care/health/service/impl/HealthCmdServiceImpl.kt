package com.health.care.health.service.impl

import com.health.care.health.controller.dto.request.HealthSaveRequest
import com.health.care.health.domain.HealthData
import com.health.care.health.mapper.HealthMapper
import com.health.care.health.repository.data.HealthDataRepository
import com.health.care.health.repository.info.HealthInfoRepository
import com.health.care.health.service.HealthCmdService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HealthCmdServiceImpl(
    private val healthInfoRepository: HealthInfoRepository,
    private val healthDataRepository: HealthDataRepository,
    private val healthMapper: HealthMapper
) : HealthCmdService {

    @Transactional
    override fun save(healthSaveRequest: HealthSaveRequest) {

        val healthInfo = this.healthMapper.toEntity(healthSaveRequest)

        this.healthInfoRepository.save(healthInfo)

        val healthDataList = healthSaveRequest.data.entries.map {
            val from = it?.period?.from
            HealthData(
                healthInfo = healthInfo,
                periodFrom = it?.period?.from,
                periodTo = it?.period?.to,
                distanceUnit = it?.distance?.unit,
                distanceValue = it?.distance?.value,
                caloriesUnit = it?.calories?.unit,
                caloriesValue = it?.calories?.value,
                steps = it?.steps,
                yearFrom = from?.year ,
                monthFrom = from?.monthValue,
                dayFrom = from?.dayOfMonth,
            )
        }
        this.healthDataRepository.saveAll(healthDataList)

    }
}