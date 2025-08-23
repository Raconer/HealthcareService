package com.health.care.config.security

import com.health.care.common.constants.GlobalConstants
import com.health.care.common.constants.ResponseMessages
import com.health.care.common.exception.UserNotFoundException
import com.health.care.sign.controller.dto.SignDTO
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtRequestFilter(
    private val jwtUtil: JwtUtil,
) : OncePerRequestFilter() {

    private val EXCLUDE_MAP: Map<String, Set<HttpMethod>> = mapOf(
        "/api/user" to setOf(HttpMethod.POST),
        "/api/sign/in" to setOf(HttpMethod.POST),
        "/api/sign/up" to setOf(HttpMethod.POST)
    )

    private val SWAGGER_EXCLUDE_PATHS = listOf(
        "/api/swagger",
        "/api/swagger/swagger-ui",
        "/api/v3/api-docs"
    )

    /**
     * 실제 필터링 로직
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val bearerToken =  requireNotNull(request.getHeader(GlobalConstants.AUTHORIZATION_HEADER)) {
            throw UserNotFoundException(ResponseMessages.TOKEN_IS_NULL)
        }

        if (bearerToken.isNotEmpty() && bearerToken.startsWith(GlobalConstants.TOKEN_PREFIX)) {
            val token: String = bearerToken.substring(GlobalConstants.SUB_LEN)

            val claims = this.jwtUtil.getData(token)
            val username: String = claims["username"] as String
            val signDTO = SignDTO(username = username)
            val authenticationToken = UsernamePasswordAuthenticationToken(signDTO, null, signDTO.authorities)
            SecurityContextHolder.getContext().authentication = authenticationToken
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val uri = request.requestURI
        val method = try {
            HttpMethod.valueOf(request.method.uppercase())
        } catch (e: IllegalArgumentException) {
            return false
        }

        if(SWAGGER_EXCLUDE_PATHS.any { path -> uri.startsWith(path) }) { return true}

        return EXCLUDE_MAP[uri]?.contains(method) == true
    }
}