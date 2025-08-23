package com.health.care.sign.domain

import com.health.care.common.domain.BasicEntity
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "`user`", catalog = "health_mng")
@DynamicUpdate
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,
    @Column(nullable = false, unique = true)
    var username:String,
    @Column(nullable = false)
    var password:String,
    @Column(nullable = false)
    var name:String,
    @Column(nullable = false)
    var nickName:String,
) : BasicEntity()