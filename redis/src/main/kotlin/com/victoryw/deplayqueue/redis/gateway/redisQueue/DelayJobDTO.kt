package com.victoryw.deplayqueue.redis.gateway.controller

import com.victoryw.deplayqueue.redis.gateway.redisQueue.IDelayJobDTO

data class DelayJobDTO(
        override val delayTo: Long,
        override val eventType: String,
        override val sourceType: String
) : IDelayJobDTO