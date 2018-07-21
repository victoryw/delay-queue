package com.victoryw.deplayqueue.redis.interfaces

import com.victoryw.deplayqueue.redis.gateway.controller.DelayJobDTO

interface IDelayQueueKeyBuilder {
    fun createDelayQueueKey(queueType: String): String
}