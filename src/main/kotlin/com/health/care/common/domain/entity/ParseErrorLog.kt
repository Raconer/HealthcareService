package com.health.care.common.domain.entity

import com.health.care.sign.domain.User
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

@Entity
@Table(name = "parse_error_log", catalog = "health_mng")
data class ParseErrorLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Comment("고유 식별자 (PK, Auto Increment)", )
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Comment("오류 발생 사용자 (User 엔티티 FK)")
    var user: User,

    @Column(name = "path")
    @Comment("오류 발생 위치 (예: JSON 경로, 필드 경로)")
    val path: String,

    @Column(name = "field")
    @Comment("오류 필드명")
    val field: String,

    @Column(name = "rejected_value", columnDefinition="TEXT")
    @Comment("거부된 값 (유효성 검사 실패 값, TEXT 타입)")
    val rejectedValue: String?,

    @Column(name = "message")
    @Comment("오류 메시지 (예: Invalid format, 필수 값 누락)")
    val message: String,

    @Column(name = "occurred_at")
    @Comment("오류 발생 시각 (기본값: 현재 시간)")
    val occurredAt: LocalDateTime = LocalDateTime.now()
)
