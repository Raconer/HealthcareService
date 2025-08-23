package com.health.care.common.exception

import com.health.care.common.constants.ResponseMessages

class MissingUserIdException(message: String = ResponseMessages.MISSING_USER_ID_ERROR) : RuntimeException(message)