package com.health.care.sign.repository

import com.health.care.sign.controller.dto.SignDTO
import com.health.care.sign.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface SignJpaRepository : JpaRepository<User, String>{

    fun findSignInfoByUsername(username: String): SignDTO?

    fun findByUsername(@Param("username") username: String): User?
}