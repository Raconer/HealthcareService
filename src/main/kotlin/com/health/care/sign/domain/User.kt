package com.health.care.sign.domain

import com.health.care.common.domain.BasicEntity
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity
@Table(name = "`user`", catalog = "health_mng")
@DynamicUpdate
data class User(
    @Id
    var id: String? = null,
    @Column(nullable = false, unique = true)
    var username:String,
    @Column(nullable = false)
    var password:String,
    @Column(nullable = false)
    var name:String,
    @Column(nullable = false)
    var nickName:String,
) : BasicEntity() {
    @PrePersist
    fun assignIdIfNull() {
        if (id == null) id = UUID.randomUUID().toString()
    }
}