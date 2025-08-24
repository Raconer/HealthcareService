package com.health.care.health.service.impl

import com.health.care.common.dto.page.PageResponse
import com.health.care.common.exception.UserNotFoundException
import com.health.care.health.controller.dto.request.HealthSearchRequest
import com.health.care.health.controller.dto.response.HealthDataResponse
import com.health.care.health.repository.data.HealthDataQueryRepository
import com.health.care.health.service.HealthReadService
import com.health.care.sign.repository.SignRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HealthReadServiceImpl(
    private val healthDataQueryRepository: HealthDataQueryRepository,
    private val signRepository: SignRepository,
) : HealthReadService {
    @Transactional(readOnly = true)
    override fun execute(healthSearchRequest: HealthSearchRequest): PageResponse<HealthDataResponse> {


        val auth = SecurityContextHolder.getContext().authentication
        val username = auth?.name
        val user = this.signRepository.findByUsername(username!!) ?: throw UserNotFoundException(username)

        val pageResult =  healthDataQueryRepository.search(healthSearchRequest,  user.id!!)
        println("-------------------------- pageResult: ${pageResult.totalElements}")
        return PageResponse(
            page = pageResult.number,
            size = pageResult.size,
            totalElements = pageResult.totalElements,
            totalPages = pageResult.totalPages,
            isLast = pageResult.isLast,
            content = HealthDataResponse(pageResult.content)
        )
    }
}