package com.health.care.sign.repository

import com.health.care.sign.controller.dto.SignDTO
import com.health.care.sign.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface SignJpaRepository : JpaRepository<User, Long>{
    // 로그인용 사용자 정보 조회 (비밀번호 포함)
    fun findSignInfoByUsername(username: String): SignDTO?
}