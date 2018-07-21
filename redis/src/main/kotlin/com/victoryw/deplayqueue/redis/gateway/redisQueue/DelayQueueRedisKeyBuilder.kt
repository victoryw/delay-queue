package com.victoryw.deplayqueue.redis.gateway.redisQueue

import com.victoryw.deplayqueue.redis.interfaces.IDelayQueueKeyBuilder
import org.springframework.stereotype.Service

@Service
class DelayQueueRedisKeyBuilder : IDelayQueueKeyBuilder {
    override fun createDelayQueueKey(queueType: String): String {
        return "delay-queue-$queueType"
    }
}