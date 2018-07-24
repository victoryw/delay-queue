package com.victoryw.deplayqueue.redis.gateway.redisQueue

import com.victoryw.deplayqueue.redis.gateway.redisQueue.scripts.popJobScript
import com.victoryw.deplayqueue.redis.interfaces.DelayJob
import com.victoryw.deplayqueue.redis.interfaces.IDelayQueueKeyBuilder
import com.victoryw.deplayqueue.redis.interfaces.QueueOperator
import io.lettuce.core.RedisClient
import io.lettuce.core.ScriptOutputType
import org.springframework.stereotype.Service

@Service
class RedisQueueOperator(
        val redisClient: RedisClient,
        delayQueueRedisKeyBuilder: IDelayQueueKeyBuilder) : QueueOperator(delayQueueRedisKeyBuilder) {

    override fun deleteQueue(queueType: String) {
        createConnection().use {
            val queueName = delayQueueRedisKeyBuilder.createDelayQueueKey(queueType)
            it.sync().del(queueName)
        }
    }

    override fun fetchMembers(queueType: String): List<DelayJob> {
        createConnection().use { connect ->
            val redisCommands = connect.sync()
            return redisCommands.zrange(delayQueueRedisKeyBuilder.createDelayQueueKey(queueType),
                    0, -1)

        }
    }

    override fun createDelayJobs(delayRequest: DelayJob) {
        createConnection().use { connect ->
            val redisCommando = connect.sync();
            val redisKey = createQueueName(delayRequest)
            redisCommando.zadd(redisKey, delayRequest.delayTo.toDouble(), delayRequest);
        }
    }

    override fun popOnTimeJob(sourceType: String, endTime: Long, callback: ((DelayJob) -> Unit)) {
        createConnection().use {
            val redisCommando = it.sync();
            val redisKey = delayQueueRedisKeyBuilder.createDelayQueueKey(sourceType)
            val onTimeJob = redisCommando.eval<DelayJob?>(
                    popJobScript,
                    ScriptOutputType.VALUE,
                    redisKey,
                    endTime.toString()
            )

            if (onTimeJob != null) {
                callback(onTimeJob)
            }

        }
    }

    private fun createConnection() = redisClient.connect(SerializedObjectCodec())!!

}


