package com.health.care.common.event.parese

import com.health.care.common.domain.entity.ParseErrorLog
import com.health.care.common.domain.entity.ParseErrorLogRepository
import com.health.care.common.dto.event.ParseErrorList
import com.health.care.common.exception.UserNotFoundException
import com.health.care.sign.repository.SignRepository
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class ParseErrorSavedListener(
    private val signRepository: SignRepository,
    private val parseErrorLogRepository: ParseErrorLogRepository,
) {

    private val log = LoggerFactory.getLogger(ParseErrorSavedListener::class.java)

    @Async
    @EventListener
    fun saveError(parseErrorList: ParseErrorList) {
        log.warn("${parseErrorList.errors.size}개 오류가 발생하였습니다.")
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth?.name
        val user = this.signRepository.findByUsername(username!!) ?: throw UserNotFoundException(username)

        val parseErrorLogList = parseErrorList.errors.map {
            ParseErrorLog(
                user = user,
                path = it.path,
                field = it.field,
                rejectedValue = it.rejectedValue,
                message = it.message,
                occurredAt = it.occurredAt
            )
        }

        this.parseErrorLogRepository.saveAll(parseErrorLogList)
    }
}