package com.example.bmta_semprace.models

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class FreedomTimer {
    private var isActive : Boolean = false
    private lateinit var startTime : LocalDateTime
    private val currentTime: LocalDateTime
        get() = LocalDateTime.now()
    private var timeElapsed: LocalDateTime = getTimeElapsed(currentTime,startTime)
        get() = getTimeElapsed(currentTime,startTime)
    private var days: Int = timeElapsed.dayOfYear
        get() = timeElapsed.dayOfYear
    private var hours: Int = timeElapsed.hour
        get() = timeElapsed.hour
    private var minutes: Int = timeElapsed.minute
        get() = timeElapsed.minute

    fun start() {
        isActive = true
        startTime = LocalDateTime.now()
    }

    private fun getTimeElapsed(toDateTime: LocalDateTime, fromDateTime: LocalDateTime): LocalDateTime{
        var tempDateTime = LocalDateTime.from(fromDateTime)

        val years = tempDateTime.until(toDateTime, ChronoUnit.YEARS)
        tempDateTime = tempDateTime.plusYears(years)

        val months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS)
        tempDateTime = tempDateTime.plusMonths(months)

        val days = tempDateTime.until(toDateTime, ChronoUnit.DAYS)
        tempDateTime = tempDateTime.plusDays(days)

        val hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS)
        tempDateTime = tempDateTime.plusHours(hours)

        val minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES)
        tempDateTime = tempDateTime.plusMinutes(minutes)

        val seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS)
        return tempDateTime
    }
}

