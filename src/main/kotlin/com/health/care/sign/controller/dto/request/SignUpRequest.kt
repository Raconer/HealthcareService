package com.health.care.sign.controller.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "회원 가입 요청")
data class SignUpRequest(

    @field:Schema(description = "이메일", example = "user")
    @field:NotBlank(message = "아이디는 필수입니다.")
    val username: String? = null,

    @field:Schema(description = "비밀번호 (최소 8자)", example = "1q2w3e4r")
    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @field:Size(min=8)
    val password: String? = null,

    @field:Schema(description = "이름", example = "홍길동")
    @field:NotBlank(message = "이름은 필수입니다.")
    val name: String? = null,

    @field:Schema(description = "닉네임", example = "홍길동")
    @field:NotBlank(message = "이름은 필수입니다.")
    val nickName: String? = null,

)