package com.victoryw.deplayqueue.redis.interfaces

interface IDelayQueueKeyBuilder {
    fun createDelayQueueKey(queueType: String): String
}