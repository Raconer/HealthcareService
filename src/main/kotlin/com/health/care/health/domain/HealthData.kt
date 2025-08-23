package com.health.care.health.domain

import com.health.care.common.domain.BasicEntity
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(catalog = "health_mng")
data class HealthData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "health_info_id")
    var healthInfo: HealthInfo,
    @Column
    val periodFrom: LocalDateTime?,
    @Column
    val periodTo: LocalDateTime?,
    @Column
    val distanceUnit: String?,
    @Column
    val distanceValue: Float?,
    @Column
    val caloriesUnit: String?,
    @Column
    val caloriesValue: Float?,
    @Column
    val steps: Int?
) : BasicEntity()