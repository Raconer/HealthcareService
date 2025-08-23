package com.health.care.common.domain.entity

import org.springframework.data.jpa.repository.JpaRepository

interface ParseErrorLogRepository : JpaRepository<ParseErrorLog, Long> {

}