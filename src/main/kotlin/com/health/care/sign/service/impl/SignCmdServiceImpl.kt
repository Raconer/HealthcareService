package com.health.care.sign.service.impl

import com.health.care.sign.controller.dto.request.SignUpRequest
import com.health.care.sign.domain.User
import com.health.care.sign.mapper.SignMapper
import com.health.care.sign.repository.SignRepository
import com.health.care.sign.service.SignCmdService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignCmdServiceImpl(
    private val signRepository: SignRepository,
    private val signMapper: SignMapper,
    private val passwordEncoder: PasswordEncoder
) : SignCmdService {
    @Transactional
    override fun signUp(signUpRequest: SignUpRequest): User {
        val user = this.signMapper.toEntity(signUpRequest, this.passwordEncoder)
        return this.signRepository.save(user)
    }
}