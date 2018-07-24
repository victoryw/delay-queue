package com.victoryw.deplayqueue.redis.gateway.redisQueue

import com.victoryw.deplayqueue.redis.interfaces.IDelayQueueKeyBuilder
import org.springframework.stereotype.Service

class DelayQueueRedisKeyBuilder : IDelayQueueKeyBuilder {
    override fun createDelayQueueKey(queueType: String): String {
        return "delayQueue:queue:$queueType"
    }
}