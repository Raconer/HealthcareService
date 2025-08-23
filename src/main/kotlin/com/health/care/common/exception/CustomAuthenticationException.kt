package com.health.care.common.exception

import org.springframework.security.core.AuthenticationException

class CustomAuthenticationException : AuthenticationException {
    constructor(msg: String?, cause: Throwable?) : super(msg, cause)
    constructor(msg: String?) : super(msg)
}