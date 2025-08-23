package com.health.care.common.error.parse

import com.health.care.common.dto.event.ParseError
import java.util.concurrent.ConcurrentLinkedQueue

object ParseErrorContext {
    private val tl = ThreadLocal.withInitial { ConcurrentLinkedQueue<ParseError>() }
    fun add(e: ParseError) { tl.get().add(e) }
    fun drain(): List<ParseError> {
        val q = tl.get()
        val out = ArrayList<ParseError>(q.size)
        while (true) q.poll()?.let(out::add) ?: break
        return out
    }
    fun clear() { tl.get().clear() }
}