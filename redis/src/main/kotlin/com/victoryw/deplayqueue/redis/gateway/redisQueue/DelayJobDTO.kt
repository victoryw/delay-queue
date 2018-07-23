package com.victoryw.deplayqueue.redis.gateway.redisQueue

import java.io.Serializable

data class DelayJobDTO(
        val delayTo: Long,
        val eventType: String,
        val sourceType: String
) : Serializable