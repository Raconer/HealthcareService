package com.health.care.sign.repository

import com.health.care.sign.domain.User
import org.springframework.stereotype.Component

@Component
class SignRepository(
    private val signJpaRepository: SignJpaRepository
) {
    fun save(user: User): User {
        return this.signJpaRepository.save(user)
    }

    fun findByUsername(username: String): User? {
        return  this.signJpaRepository.findByUsername(username)
    }
}