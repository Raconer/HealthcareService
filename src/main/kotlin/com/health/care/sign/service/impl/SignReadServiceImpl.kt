package com.health.care.sign.service.impl

import com.health.care.common.constants.ResponseMessages
import com.health.care.config.security.JwtUtil
import com.health.care.sign.controller.dto.request.SignInRequest
import com.health.care.sign.controller.dto.response.SignInResponse
import com.health.care.sign.repository.SignJpaRepository
import com.health.care.sign.service.SignReadService
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignReadServiceImpl(
    private val signJpaRepository: SignJpaRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) : SignReadService {

    @Transactional(readOnly = true)
    override fun signIn(signInRequest: SignInRequest): SignInResponse {
        val username = signInRequest.username!!

        // 사용자 조회
        val signDto = this.signJpaRepository.findSignInfoByUsername(username)?: throw UsernameNotFoundException(
            ResponseMessages.USER_NOT_FOUND)

        if(!this.passwordEncoder.matches(signInRequest.password, signDto.password)){
            throw BadCredentialsException(ResponseMessages.PASSWORD_NOT_MATCH) // 비밀번호가 틀렸습니다로 작성하면 사용자 정보 노출 위험이 있으므로 사용자
        }

        // JWT 토큰 발급
        return SignInResponse(username, this.jwtUtil.create(username))
    }
}