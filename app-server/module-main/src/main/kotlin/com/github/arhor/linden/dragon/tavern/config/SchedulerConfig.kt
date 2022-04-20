package com.github.arhor.linden.dragon.tavern.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@Configuration(proxyBeanMethods = false)
class SchedulerConfig
