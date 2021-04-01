package org.arhor.diploma.config

import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableAsync
@EnableScheduling
@Configuration
class AsyncConfig(private val props: TaskExecutionProperties) : AsyncConfigurer {

    @Override
    override fun getAsyncUncaughtExceptionHandler() = SimpleAsyncUncaughtExceptionHandler()
}
