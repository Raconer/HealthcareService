package com.health.care.sign.controller

import com.health.care.common.reponse.CommonRes
import com.health.care.sign.controller.dto.request.SignInRequest
import com.health.care.sign.controller.dto.request.SignUpRequest
import com.health.care.sign.service.SignCmdService
import com.health.care.sign.service.SignReadService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@Tag(name = "사용자 API", description = "로그인 관련 API")
@RestController
@RequestMapping("/sign")
class SignController(
    private val signReadService: SignReadService,
    private val signCmdService: SignCmdService
) {
    @Operation(
        summary = "회원가입",
        description = "사용자 정보를 입력받아 회원가입을 수행합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "회원가입 성공"
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (유효성 검사 실패)"
            ),
            ApiResponse(
                responseCode = "500",
                description = "서버 내부 오류"
            )
        ]
    )
    @PostMapping("/up")
    fun signUp(
        @SwaggerRequestBody(
            description = "회원가입 요청 정보",
            required = true,
            content = [Content(schema = Schema(implementation = SignUpRequest::class))]
        )
        @Valid @RequestBody signUpRequest: SignUpRequest) : ResponseEntity<out Any>{
        return CommonRes.Def(this.signCmdService.signUp(signUpRequest))
    }

    @Operation(
        summary = "로그인",
        description = "아이디와 비밀번호로 로그인을 시도합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "로그인 성공",
                content = [Content(schema = Schema(implementation = SignInRequest::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (유효성 검사 실패)"
            ),
            ApiResponse(
                responseCode = "500",
                description = "서버 내부 오류"
            )
        ]
    )
    @PostMapping("/in")
    fun signIn(
        @SwaggerRequestBody(
            description = "로그인 요청 정보",
            required = true,
            content = [Content(schema = Schema(implementation = SignInRequest::class))]
        )
        @Valid @RequestBody signInRequest: SignInRequest): ResponseEntity<out Any> {
        return CommonRes.Def(this.signReadService.signIn(signInRequest))
    }
}