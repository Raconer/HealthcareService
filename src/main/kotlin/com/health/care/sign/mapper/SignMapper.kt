package com.health.care.sign.mapper

import com.health.care.sign.controller.dto.request.SignUpRequest
import com.health.care.sign.domain.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.springframework.security.crypto.password.PasswordEncoder

@Mapper(componentModel = "spring")
interface SignMapper {
    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(
            target = "password",
            expression = "java(passwordEncoder.encode(signUpRequest.getPassword()))" // 비밀번호는 암호화하여 저장
        )
    )
    fun toEntity(signUpRequest: SignUpRequest, passwordEncoder: PasswordEncoder) : User
}