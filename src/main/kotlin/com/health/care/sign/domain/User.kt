package com.health.care.sign.domain

import com.health.care.common.domain.BasicEntity
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity
@Table(name = "`user`", catalog = "health_mng")
@DynamicUpdate
data class User(
    @Id
    @Column(name = "id")
    @Comment("고유 식별자 (UUID, 엔티티 저장 시 자동 생성)")
    var id: String? = null,

    @Column(nullable = false, unique = true)
    @Comment("사용자 계정 (로그인 아이디, 중복 불가)")
    var username:String,
    @Column(nullable = false)
    @Comment("사용자 비밀번호 (암호화 저장 필요)")
    var password:String,
    @Column(nullable = false)
    @Comment("사용자 이름")
    var name:String,
    @Column(nullable = false)
    @Comment("사용자 닉네임")
    var nickName:String,
) : BasicEntity() {
    @PrePersist
    fun assignIdIfNull() {
        if (id == null) id = UUID.randomUUID().toString()
    }
}