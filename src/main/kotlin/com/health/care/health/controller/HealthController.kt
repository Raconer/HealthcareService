package com.health.care.health.controller

import com.health.care.common.dto.event.ParseErrorList
import com.health.care.common.error.parse.ParseErrorContext
import com.health.care.health.controller.dto.request.HealthSaveRequest
import com.health.care.health.service.HealthCmdService
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Health API", description = "Health 관련 API")
@RestController
@RequestMapping("/health")
class HealthController(
    private val healthCmdService: HealthCmdService,
    private val eventPublisher: ApplicationEventPublisher
) {

    private val log = LoggerFactory.getLogger(HealthController::class.java)

    @PostMapping
    fun save(
        @RequestBody healthSaveRequest: HealthSaveRequest
    ) : ResponseEntity<Void> {
        val parseErrorList = ParseErrorContext.drain()
        if(parseErrorList.isNotEmpty()){
            log.warn("잘못된 양식의 데이터가 있습니다.")
            healthSaveRequest.data.entries = healthSaveRequest.data.entries.filterNotNull()
            this.eventPublisher.publishEvent(ParseErrorList(parseErrorList))
        }

        this.healthCmdService.save(healthSaveRequest)

        return ResponseEntity.ok().build()
    }
}