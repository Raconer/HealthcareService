package com.health.care.health.controller

import com.health.care.common.dto.event.ParseErrorList
import com.health.care.common.error.parse.ParseErrorContext
import com.health.care.common.reponse.CommonRes
import com.health.care.health.controller.dto.request.HealthSaveRequest
import com.health.care.health.controller.dto.request.HealthSearchRequest
import com.health.care.health.service.HealthCmdService
import com.health.care.health.service.HealthReadService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Health API", description = "Health 관련 API")
@RestController
@RequestMapping("/health")
class HealthController(
    private val healthReadService: HealthReadService,
    private val healthCmdService: HealthCmdService,
    private val eventPublisher: ApplicationEventPublisher
) {

    private val log = LoggerFactory.getLogger(HealthController::class.java)

    @PostMapping
    fun save(
        @RequestBody healthSaveRequest: HealthSaveRequest
    ) : ResponseEntity<Any> {
        val parseErrorList = ParseErrorContext.drain()
        if(parseErrorList.isNotEmpty()){
            log.warn("잘못된 양식의 데이터가 있습니다.")
            healthSaveRequest.data.entries = healthSaveRequest.data.entries.filterNotNull()
            this.eventPublisher.publishEvent(ParseErrorList(parseErrorList))
        }

        this.healthCmdService.save(healthSaveRequest)

        return CommonRes.Basic(HttpStatus.OK)
    }

    // 회원 조회
    @Operation(
        summary = "건강 목록 조회",
        description = "건강 정보 회원 목록을 조회합니다.",
        responses = [
            ApiResponse(responseCode = "200", description = "조회 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청"),
            ApiResponse(responseCode = "500", description = "서버 오류")
        ]
    )
    @GetMapping
    fun searchUsers(
        @Parameter(description = "건강 목록 조회 (페이지, 사이즈)", required = true)
        healthSearchRequest: HealthSearchRequest): ResponseEntity<out Any> {

        return CommonRes.Def(this.healthReadService.execute(healthSearchRequest))
    }
}