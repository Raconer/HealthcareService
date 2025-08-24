package com.health.care.health.domain

import com.health.care.common.domain.BasicEntity
import jakarta.persistence.*
import org.hibernate.annotations.Comment
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
    @Column(name = "id")
    @Comment("고유 식별자 (PK, Auto Increment)")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_info_id", nullable = false)
    @Comment("연결된 HealthInfo (FK)")
    var healthInfo: HealthInfo,

    @Column(name = "period_from")
    @Comment("데이터 기록 시작 시각")
    val periodFrom: LocalDateTime? = null,

    @Column(name = "period_to")
    @Comment("데이터 기록 종료 시각")
    val periodTo: LocalDateTime? = null,

    @Column(name = "distance_unit")
    @Comment("이동 거리 단위 (예: km, m)")
    val distanceUnit: String? = null,

    @Column(name = "distance_value")
    @Comment("이동 거리 값")
    val distanceValue: Float? = null,

    @Column(name = "calories_unit")
    @Comment("칼로리 단위 (예: kcal)")
    val caloriesUnit: String? = null,

    @Column(name = "calories_value")
    @Comment("칼로리 소모 값")
    val caloriesValue: Float? = null,

    @Column(name = "steps")
    @Comment("걸음 수")
    val steps: Int? = null,

    @Column(name = "year_from")
    @Comment("기록 시작 연도")
    val yearFrom: Int? = null,

    @Column(name = "month_from")
    @Comment("기록 시작 월")
    val monthFrom: Int? = null,

    @Column(name = "day_from")
    @Comment("기록 시작 일")
    val dayFrom: Int? = null,

) : BasicEntity()