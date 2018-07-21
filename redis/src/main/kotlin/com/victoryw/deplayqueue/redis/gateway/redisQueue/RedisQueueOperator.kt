package com.victoryw.deplayqueue.redis.gateway.redisQueue

import com.victoryw.deplayqueue.redis.interfaces.IDelayQueueKeyBuilder
import io.lettuce.core.RedisClient
import org.springframework.stereotype.Service

@Service
class RedisQueueOperator(
        val redisClient: RedisClient,
        val delayQueueRedisKeyBuilder: IDelayQueueKeyBuilder) {

    fun deleteQueue(queueType: String) {
        redisClient.connect().use {
            val queueName = delayQueueRedisKeyBuilder.createDelayQueueKey(queueType)
            it.sync().del(queueName)
        }
    }

    fun fetchQueueMembers(sourceType: String): List<String>? {
        return redisClient.connect().use { connect ->
            val redisCommands = connect.sync()
            return@use redisCommands.zrange(delayQueueRedisKeyBuilder.createDelayQueueKey(sourceType),
                    0, -1)

        }
    }


}


