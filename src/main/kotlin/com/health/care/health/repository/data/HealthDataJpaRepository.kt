package com.health.care.health.repository.data

import com.health.care.health.domain.HealthData
import com.health.care.health.domain.HealthInfo
import org.springframework.data.jpa.repository.JpaRepository

interface HealthDataJpaRepository : JpaRepository<HealthData, Long> {
}