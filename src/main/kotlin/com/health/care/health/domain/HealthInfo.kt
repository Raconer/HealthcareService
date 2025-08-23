package com.health.care.health.domain

import com.health.care.common.domain.BasicEntity
import com.health.care.health.domain.enums.HealthInfoType
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Entity
@Table(name= "health_info", catalog = "health_mng")
data class HealthInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "record_key", nullable = false, length = 40)
    val recordKey: String,
    @Column(name = "memo", columnDefinition = "TEXT")
    var memo: String? = null,
    @Column(name = "last_update")
    var lastUpdate: OffsetDateTime? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 10)
    var type: HealthInfoType,
    @Column(name = "product_name", length = 64)
    var productName: String? = null,
    @Column(name = "product_vendor", length = 64)
    var productVendor: String? = null,
    @Column(name = "source_type", length = 64)
    var sourceType: String? = null,
    @Column(name = "source_mode")
    var sourceMode: Int? = null,
    @Column(name = "source_name", length = 64)
    var sourceName: String? = null,

    @OneToMany(mappedBy = "healthInfo")
    val healthDataList : MutableList<HealthData>?
) : BasicEntity() {

}