package com.victoryw.deplayqueue.redis.gateway.redisQueue

import com.fasterxml.jackson.databind.ObjectMapper
import com.victoryw.deplayqueue.redis.gateway.controller.DelayJobDTO
import com.victoryw.deplayqueue.redis.interfaces.IDelayQueueKeyBuilder
import io.lettuce.core.RedisClient
import org.springframework.stereotype.Service

@Service
class RedisQueueOperator(
        val redisClient: RedisClient,
        val delayQueueRedisKeyBuilder: IDelayQueueKeyBuilder,
        var objectMapper: ObjectMapper) {

    fun deleteQueue(queueType: String) {
        createConnection().use {
            val queueName = delayQueueRedisKeyBuilder.createDelayQueueKey(queueType)
            it.sync().del(queueName)
        }
    }

    fun fetchQueueMembers(sourceType: String): List<String>? {
        return createConnection().use { connect ->
            val redisCommands = connect.sync()
            return@use redisCommands.zrange(delayQueueRedisKeyBuilder.createDelayQueueKey(sourceType),
                    0, -1)

        }
    }

    fun createDelayJobs(delayRequest: DelayJobDTO) {
        createConnection().use { connect ->
            val redisCommando = connect.sync();
            val redisKey = delayQueueRedisKeyBuilder.createDelayQueueKey(delayRequest.sourceType)
            redisCommando.zadd(redisKey, delayRequest.delayTo.toDouble(), objectMapper.writeValueAsString(delayRequest));
        }
    }

    fun createConnection() = redisClient.connect()!!

}


