package com.github.arhor.linden.dragon.tavern.service.impl

import com.github.arhor.linden.dragon.tavern.common.time.DEFAULT_ZONE_ID
import com.github.arhor.linden.dragon.tavern.common.time.TIME_MEASUREMENT_ACCURACY
import com.github.arhor.linden.dragon.tavern.service.TimeService
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.util.TimeZone

@Service
class TimeServiceImpl : TimeService {

    override val now: Instant
        get() = currentInstant(DEFAULT_ZONE_ID)

    override val weekAgo: Instant
        get() = now - Duration.ofDays(7)

    override fun now(timeZone: TimeZone?): Instant {
        return currentInstant(timeZone?.toZoneId() ?: DEFAULT_ZONE_ID)
    }

    private fun currentInstant(zoneId: ZoneId): Instant {
        return Clock.system(zoneId).instant().truncatedTo(TIME_MEASUREMENT_ACCURACY)
    }
}
