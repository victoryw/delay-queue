package com.victoryw.deplayqueue.redis.interfaces

class DelayJobWorker(
        private val queueOperator: QueueOperator,
        private val delayJobConsumer: IDelayJobConsumer
) {
    fun runOnce(sourceType: String, endTime: Long) {
        this.queueOperator.popOnTimeJob(
                sourceType,
                endTime,
                this.delayJobConsumer::consume
        )
    }
}
