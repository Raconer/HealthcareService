package com.health.care.sign.service

import com.health.care.sign.controller.dto.request.SignUpRequest
import com.health.care.sign.domain.User

interface SignCmdService {
    fun signUp(signUpRequest: SignUpRequest) : User
}