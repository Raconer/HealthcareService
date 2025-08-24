package com.health.care.common.domain.entity

import com.health.care.sign.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "parse_error_log", catalog = "health_mng")
data class ParseErrorLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,
    val path: String,
    val field: String,
    @Column(columnDefinition="TEXT")
    val rejectedValue: String?,
    val message: String,
    val occurredAt: LocalDateTime = LocalDateTime.now()
)
