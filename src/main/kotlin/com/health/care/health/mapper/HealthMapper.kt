package com.health.care.health.mapper

import com.health.care.health.controller.dto.request.HealthSaveRequest
import com.health.care.health.domain.HealthData
import com.health.care.health.domain.HealthInfo
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface HealthMapper {


    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "recordKey", source = "recordkey"),
        Mapping(target = "lastUpdate", source = "lastUpdate"),
        Mapping(target = "memo", source = "data.memo"),
        Mapping(target = "type", source = "type"),
        Mapping(target = "productName", source = "data.source.product.name"),
        Mapping(target = "productVendor", source = "data.source.product.vender"),
        Mapping(target = "sourceType", source = "data.source.type"),
        Mapping(target = "sourceMode", source = "data.source.mode"),
        Mapping(target = "sourceName", source = "data.source.name"),
        Mapping(target = "healthDataList", ignore = true)
    )
    fun toEntity(healthSaveRequest: HealthSaveRequest): HealthInfo

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "healthInfo", ignore = true), // 역참조는 AfterMapping에서
        Mapping(target = "periodFrom", source = "period.from"),
        Mapping(target = "periodTo", source = "period.to"),
        Mapping(target = "distanceUnit", source = "distance.unit"),
        Mapping(target = "distanceValue", source = "distance.value"),
        Mapping(target = "caloriesUnit", source = "calories.unit"),
        Mapping(target = "caloriesValue", source = "calories.value"),
        Mapping(target = "steps", source = "steps")
    )
    fun toHealthData(e: HealthSaveRequest.Entry): HealthData
}