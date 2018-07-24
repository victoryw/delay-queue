package com.victoryw.deplayqueue.redis.interfaces

abstract class QueueOperator(val delayQueueRedisKeyBuilder: IDelayQueueKeyBuilder) {
    abstract fun deleteQueue(queueType: String)

    abstract fun fetchMembers(queueType: String): List<DelayJob>

    abstract fun createDelayJobs(delayRequest: DelayJob)

    protected fun createQueueName(delayRequest: DelayJob) =
            delayQueueRedisKeyBuilder.createDelayQueueKey(delayRequest.sourceType)

    abstract fun popOnTimeJob(sourceType: String, endTime: Long, callback: (DelayJob) -> Unit)
}