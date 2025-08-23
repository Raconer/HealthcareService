package com.health.care.health.repository.info

import com.health.care.health.domain.HealthInfo
import org.springframework.stereotype.Component

@Component
class HealthInfoRepository(
    private val healthInfoJpaRepository: HealthInfoJpaRepository
) {
    fun save(healthInfo: HealthInfo): HealthInfo {
        return healthInfoJpaRepository.save(healthInfo)
    }
}