package com.health.care.health.repository.info

import com.health.care.health.domain.HealthInfo
import org.springframework.data.jpa.repository.JpaRepository

interface HealthInfoJpaRepository : JpaRepository<HealthInfo, Long> {
}