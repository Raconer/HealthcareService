package com.health.care.health.controller.dto.response

data class HealthDataResponse(
    val userDataList:List<HealthUserData> = mutableListOf(),
) {
    data class HealthUserData(
        val date: String,
        val steps: Int? = null,
        val calories: Float? = null,
        val distance: Float? = null,
        val recordkey:String? = null
    )
}