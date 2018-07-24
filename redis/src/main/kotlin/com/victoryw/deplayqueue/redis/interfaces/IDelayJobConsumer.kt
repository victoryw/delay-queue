package com.victoryw.deplayqueue.redis.interfaces

import org.springframework.stereotype.Service

interface IDelayJobConsumer {
    fun consume(delayJob: DelayJob)

}

class EmptyDelayJobConsumer: IDelayJobConsumer {
    override fun consume(delayJob: DelayJob) {}
}
