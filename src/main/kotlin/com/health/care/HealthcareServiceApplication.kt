package com.health.care

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HealthcareServiceApplication

fun main(args: Array<String>) {
    runApplication<HealthcareServiceApplication>(*args)
}
