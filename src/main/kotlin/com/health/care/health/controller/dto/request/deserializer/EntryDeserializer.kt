package com.health.care.health.controller.dto.request.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.health.care.common.dto.event.ParseError
import com.health.care.common.error.parse.ParseErrorContext
import com.health.care.health.controller.dto.request.HealthSaveRequest
import org.springframework.boot.jackson.JsonComponent

@JsonComponent
class EntryDeserializer : JsonDeserializer<HealthSaveRequest.Entry>(){

    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext?): HealthSaveRequest.Entry? {
        val mapper = parser.codec as ObjectMapper
        val node = mapper.readTree<JsonNode>(parser)

        val period = mapper.treeToValue(node.get("period"), HealthSaveRequest.Period::class.java)
        val calories = node.get("calories")?.let { mapper.treeToValue(it, HealthSaveRequest.Quantity::class.java) }
        val distance = node.get("distance")?.let { mapper.treeToValue(it, HealthSaveRequest.Quantity::class.java) }
        val steps: Int = this.checkStep(parser, node.get("steps")) ?: return null

        return HealthSaveRequest.Entry(
            period = period,
            steps = steps,
            calories = calories,
            distance = distance
        )
    }

    private fun checkStep(parser:JsonParser, stepsNode:JsonNode?):Int? {
        val steps = when {
            stepsNode == null || stepsNode.isNull -> null
            stepsNode.isInt -> stepsNode.intValue()
            stepsNode.isIntegralNumber -> stepsNode.asInt()
            stepsNode.isTextual -> {
                val txt = stepsNode.asText()
                txt.toIntOrNull()
            }
            else -> {
                null
            }
        }

        if(steps == null) {
            addErr(parser, "steps", stepsNode.toString(), "steps must be integer")
        }

        return steps
    }

    private fun addErr(p: JsonParser, field: String, rejected: String?, msg: String) {
        val ptr = p.parsingContext?.pathAsPointer()?.toString() ?: "$"
        ParseErrorContext.add(
            ParseError(
                path = (if (ptr.endsWith("/")) ptr else "$ptr/") + field,
                field = field,
                rejectedValue = rejected,
                message = msg
            )
        )
    }
}