package com.victoryw.deplayqueue.redis.configuration

import com.victoryw.deplayqueue.redis.interfaces.DelayJobWorker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

const val SECOND = (1 * 1000).toLong()

@Configuration
class RedisQueueMonitorConfig {
    @Bean
    fun createRedisQueueEnabled(): RedisQueueEnableSolution {
        return RedisQueueEnableSolution();
    }
}

@Component
class RedisQueueMonitor(val delayJobWorker: DelayJobWorker,
                        val redisQueueCreator: RedisQueueEnableSolution) {
    @Scheduled(fixedRate = SECOND * 60)
    fun scheduleToMonitor() {
        if (redisQueueCreator.isEnabled()) {
            delayJobWorker.runOnce("queue", Date().time)
        }

    }
}

open class RedisQueueEnableSolution {
    open fun isEnabled(): Boolean {
        return true;
    }
}

