package com.health.care.health.repository.data

import com.health.care.health.controller.dto.request.HealthSearchRequest
import com.health.care.health.controller.dto.response.HealthDataResponse
import com.health.care.health.domain.QHealthData.healthData
import com.health.care.health.domain.QHealthInfo.healthInfo
import com.health.care.health.domain.enums.SearchType
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Expression
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.StringTemplate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Repository
@Transactional(readOnly = true)
class HealthDataQueryRepository (
    private val queryFactory: JPAQueryFactory
){
    fun search(healthSearchRequest: HealthSearchRequest, recordkey:String): Page<HealthDataResponse.HealthUserData> {
        val pageable : Pageable = PageRequest.of(healthSearchRequest.page - 1, healthSearchRequest.size)
        val condition = this.condition(recordkey, healthSearchRequest.from, healthSearchRequest.to)
        val searchType = healthSearchRequest.searchType

        val groupBy = this.groupBy(searchType)
        val query = this.queryFactory
                                                .from(healthData)
                                                .join(healthData.healthInfo, healthInfo)
                                                .where(condition)


        val date = this.date(searchType)

        val count = query.select(Expressions.numberTemplate(
            Long::class.java,
            "COUNT(DISTINCT {0}, {1}, {2})",
            healthData.yearFrom,
            healthData.monthFrom,
            healthData.dayFrom
        ) ).fetchOne()

        val content = query
            .select(
                Projections.constructor(
                    HealthDataResponse.HealthUserData::class.java,
                    date,
                    healthData.steps.sum(),
                    healthData.distanceValue.sum(),
                    healthData.caloriesValue.sum(),
                    healthInfo.recordKey
                )
            )
            .limit(pageable.pageSize.toLong())
            .orderBy(*this.orderBy(searchType))
            .groupBy(*groupBy)
            .offset(pageable.offset)
            .fetch()

        println(content.size)



        return PageableExecutionUtils.getPage(content, pageable) { count ?: 0L }
    }

    private fun date(searchType:SearchType):StringTemplate = when(searchType){
        SearchType.MONTH -> {
            Expressions.stringTemplate(
                "CONCAT({0}, '-', LPAD({1}, 2, '0'))",
                healthData.yearFrom.stringValue(),
                healthData.monthFrom.stringValue()
            )
        }
        else -> {
            Expressions.stringTemplate(
                "CONCAT({0}, '-', LPAD({1}, 2, '0'), '-', LPAD({2}, 2, '0'))",
                healthData.yearFrom.stringValue(),
                healthData.monthFrom.stringValue(),
                healthData.dayFrom.stringValue()
            )
        }
    }

    private fun condition(recordkey:String, from:LocalDate, to: LocalDate) : BooleanBuilder = BooleanBuilder()
        .and(healthInfo.recordKey.eq(recordkey))
        .also {
            from.atStartOfDay().let { start -> it.and(healthData.periodFrom.goe(start)) }
            to.atStartOfDay().let { end -> it.and(healthData.periodFrom.lt(end)) }
        }

    private fun groupBy(searchType:SearchType): Array<Expression<*>> = when (searchType) {
        SearchType.MONTH -> {
            arrayOf(healthData.yearFrom, healthData.monthFrom)
        }
        else -> {
            arrayOf(healthData.yearFrom, healthData.monthFrom, healthData.dayFrom)
        }
    }

    private fun orderBy(searchType:SearchType): Array<OrderSpecifier<*>> = when (searchType) {
        SearchType.MONTH -> {
            arrayOf(  healthData.yearFrom.desc(), healthData.monthFrom.desc())
        }
        else -> {
            arrayOf(  healthData.yearFrom.desc(), healthData.monthFrom.desc(), healthData.dayFrom.desc())
        }
    }


}