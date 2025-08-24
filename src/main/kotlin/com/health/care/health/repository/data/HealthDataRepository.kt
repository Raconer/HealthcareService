package com.health.care.health.repository.data

import com.health.care.health.domain.HealthData
import org.springframework.stereotype.Component

@Component
class HealthDataRepository(
    private val healthDataJpaRepository: HealthDataJpaRepository
) {
    fun saveAll(healthDataList: List<HealthData>) : List<HealthData> {
        return healthDataJpaRepository.saveAll(healthDataList)
    }


}