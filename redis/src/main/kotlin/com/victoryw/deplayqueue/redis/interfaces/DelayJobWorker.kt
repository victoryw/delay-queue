package com.victoryw.deplayqueue.redis.interfaces

import com.victoryw.deplayqueue.redis.gateway.redisQueue.RedisQueueOperator
import org.springframework.stereotype.Service

@Service
class DelayJobWorker(
        val redisQueueOperator: RedisQueueOperator,
        val delayJobConsumer: IDelayJobConsumer
) {
    fun runOnce(sourceType: String, endTime: Long) {
        val delayJob = this.redisQueueOperator.popOnTimeJob(
                sourceType,
                endTime,
                this.delayJobConsumer::consume
        )
    }
}
