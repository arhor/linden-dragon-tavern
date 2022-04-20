package com.github.arhor.linden.dragon.tavern.service

import java.time.Instant
import java.util.TimeZone

interface TimeService {

    val now: Instant

    val weekAgo: Instant

    fun now(timeZone: TimeZone?): Instant
}
