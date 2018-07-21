package com.victoryw.deplayqueue.redis.gateway.redisQueue

interface IDelayJobDTO {
    val delayTo: Long
    val eventType: String
    val sourceType: String
}