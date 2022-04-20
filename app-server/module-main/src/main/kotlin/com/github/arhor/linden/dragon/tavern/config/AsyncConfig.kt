package com.github.arhor.linden.dragon.tavern.config

import com.github.arhor.linden.dragon.tavern.infrastructure.context.CurrentRequestContext
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor
import org.springframework.web.context.request.RequestContextHolder
import java.util.concurrent.Callable
import java.util.concurrent.Executor

@EnableAsync
@Configuration(proxyBeanMethods = false)
class AsyncConfig(private val currentRequestContext: CurrentRequestContext) : AsyncConfigurer {

    @Bean
    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        return SimpleAsyncUncaughtExceptionHandler()
    }

    @Bean
    override fun getAsyncExecutor(): Executor {
        return DelegatingSecurityContextAsyncTaskExecutor(
            ContextAwareThreadPoolTaskExecutor().apply {
                initialize()
            }
        )
    }

    private inner class ContextAwareThreadPoolTaskExecutor : ThreadPoolTaskExecutor() {

        override fun <T> submit(task: Callable<T>) = super.submit(wrap(task))

        override fun <T> submitListenable(task: Callable<T>) = super.submitListenable(wrap(task))

        override fun submit(task: Runnable) = super.submit(wrap(task))

        override fun submitListenable(task: Runnable) = super.submitListenable(wrap(task))

        override fun execute(task: Runnable) = super.execute(wrap(task))

        override fun execute(task: Runnable, startTimeout: Long) = super.execute(wrap(task), startTimeout)

        private fun <T> wrap(task: Callable<T>): Callable<T> {
            val parentThreadRequestAttributes = RequestContextHolder.currentRequestAttributes()
            val parentThreadRequestId = currentRequestContext.requestId
            return Callable<T> {
                try {
                    RequestContextHolder.setRequestAttributes(parentThreadRequestAttributes)
                    currentRequestContext.requestId = parentThreadRequestId
                    return@Callable task.call()
                } finally {
                    RequestContextHolder.resetRequestAttributes()
                    currentRequestContext.requestId = null
                }
            }
        }

        private fun wrap(task: Runnable): Runnable {
            val parentThreadRequestAttributes = RequestContextHolder.currentRequestAttributes()
            val parentThreadRequestId = currentRequestContext.requestId
            return Runnable {
                try {
                    RequestContextHolder.setRequestAttributes(parentThreadRequestAttributes)
                    currentRequestContext.requestId = parentThreadRequestId
                    task.run()
                } finally {
                    RequestContextHolder.resetRequestAttributes()
                    currentRequestContext.requestId = null
                }
            }
        }
    }
}
