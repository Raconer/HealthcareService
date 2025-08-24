package com.health.care.health.domain

import com.health.care.common.domain.BasicEntity
import com.health.care.health.domain.enums.HealthInfoType
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.OffsetDateTime

@Entity
@Table(name= "health_info", catalog = "health_mng")
data class HealthInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유 식별자 (PK, Auto Increment)")
    val id: Long? = null,

    @Column(name = "record_key", nullable = false, length = 40)
    @Comment("사용자 구분 키 (recordKey)")
    val recordKey: String,

    @Column(name = "memo", columnDefinition = "TEXT")
    @Comment("추가 메모 (TEXT)")
    var memo: String? = null,

    @Column(name = "last_update")
    @Comment("마지막 업데이트 시각")
    var lastUpdate: OffsetDateTime? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 10)
    @Comment("헬스 정보 타입 (예: 걸음수, 심박수 등)")
    var type: HealthInfoType,

    @Column(name = "product_name", length = 64)
    @Comment("데이터를 수집한 제품 이름")
    var productName: String? = null,

    @Column(name = "product_vendor", length = 64)
    @Comment("데이터를 수집한 벤더/제조사")
    var productVendor: String? = null,

    @Column(name = "source_type", length = 64)
    @Comment("데이터 소스 타입")
    var sourceType: String? = null,

    @Column(name = "source_mode")
    @Comment("데이터 소스 모드")
    var sourceMode: Int? = null,

    @Column(name = "source_name", length = 64)
    @Comment("데이터 소스 이름")
    var sourceName: String? = null,

    @OneToMany(mappedBy = "healthInfo")
    @Comment("연결된 HealthData 목록 (1:N)")
    val healthDataList : MutableList<HealthData>?
) : BasicEntity() {

}