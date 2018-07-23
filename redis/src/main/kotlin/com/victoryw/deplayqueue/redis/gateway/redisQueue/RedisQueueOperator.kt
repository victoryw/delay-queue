package com.victoryw.deplayqueue.redis.gateway.redisQueue

import com.victoryw.deplayqueue.redis.interfaces.IDelayQueueKeyBuilder
import io.lettuce.core.RedisClient
import org.springframework.stereotype.Service

@Service
class RedisQueueOperator(
        val redisClient: RedisClient,
        val delayQueueRedisKeyBuilder: IDelayQueueKeyBuilder) {

    fun deleteQueue(queueType: String) {
        createConnection().use {
            val queueName = delayQueueRedisKeyBuilder.createDelayQueueKey(queueType)
            it.sync().del(queueName)
        }
    }

    fun fetchMembers(queueType: String): List<DelayJobDTO> {
        createConnection().use { connect ->
            val redisCommands = connect.sync()
            return redisCommands.zrange(delayQueueRedisKeyBuilder.createDelayQueueKey(queueType),
                    0, -1)

        }
    }

    fun createDelayJobs(delayRequest: DelayJobDTO) {
        createConnection().use { connect ->
            val redisCommando = connect.sync();
            val redisKey = delayQueueRedisKeyBuilder.createDelayQueueKey(delayRequest.sourceType)
            redisCommando.zadd(redisKey, delayRequest.delayTo.toDouble(), delayRequest);
        }
    }

    fun createConnection() = redisClient.connect(SerializedObjectCodec())!!

}


