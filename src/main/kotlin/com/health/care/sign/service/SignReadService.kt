package com.health.care.sign.service

import com.health.care.sign.controller.dto.request.SignInRequest
import com.health.care.sign.controller.dto.response.SignInResponse


interface SignReadService {
    fun signIn(signInRequest: SignInRequest): SignInResponse
}