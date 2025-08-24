package com.health.care.health.domain

import com.health.care.common.domain.BasicEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "health_data",
    catalog = "health_mng",
  indexes = [  // 선택
      Index(name = "ix_hd_fk_year_from", columnList = "health_info_id, year_from"),
      Index(name = "ix_hd_fk_month_from", columnList = "health_info_id, month_from"),
      Index(name = "ix_hd_fk_day_from", columnList = "health_info_id, day_from")
  ]
)
data class HealthData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_info_id", nullable = false)
    var healthInfo: HealthInfo,

    @Column(name = "period_from")
    val periodFrom: LocalDateTime? = null,

    @Column(name = "period_to")
    val periodTo: LocalDateTime? = null,

    @Column(name = "distance_unit")
    val distanceUnit: String? = null,

    @Column(name = "distance_value")
    val distanceValue: Float? = null,

    @Column(name = "calories_unit")
    val caloriesUnit: String? = null,

    @Column(name = "calories_value")
    val caloriesValue: Float? = null,

    @Column(name = "steps")
    val steps: Int? = null,

    @Column(name = "year_from")
    val yearFrom: Int? = null,

    @Column(name = "month_from")
    val monthFrom: Int? = null,

    @Column(name = "day_from")
    val dayFrom: Int? = null,

) : BasicEntity()