package com.health.care.common.exception

import com.health.care.common.constants.ResponseMessages
import com.health.care.common.dto.exception.FieldError
import com.health.care.common.reponse.CommonRes
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 전역 예외 처리 핸들러.
 * 컨트롤러에서 발생한 예외를 공통 포맷으로 응답.
 */

@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * @param ex 유효성 검증 실패 예외
     * @return 필드별 에러 메시지 리스트를 포함한 400 BAD_REQUEST 응답
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationEx(ex: MethodArgumentNotValidException ): ResponseEntity<Any> {

        val errors = ex.bindingResult.fieldErrors.map {
            FieldError(it.field, it.defaultMessage?: ResponseMessages.UNKNOWN_ERROR)
        }
        return CommonRes.Except(HttpStatus.BAD_REQUEST, errors)
    }

    /**
     * @param ex 모든 예외
     * @return 일반 서버 오류 응답 (500 INTERNAL_SERVER_ERROR)
     */
    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception): ResponseEntity<Any> {
        val message = ex.message ?: ResponseMessages.UNKNOWN_ERROR
        return CommonRes.Except(HttpStatus.INTERNAL_SERVER_ERROR, message)
    }

    /**
     * @param ex 중복 오류
     * @return 일반 서버 오류 응답 (500 INTERNAL_SERVER_ERROR)
     */
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDuplicateKey(ex: DataIntegrityViolationException): ResponseEntity<Any> {
        if (ex.message?.contains("uk_user_username") == true) {
            return CommonRes.Except(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessages.DUPLICATE_USER)
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류")
    }
}