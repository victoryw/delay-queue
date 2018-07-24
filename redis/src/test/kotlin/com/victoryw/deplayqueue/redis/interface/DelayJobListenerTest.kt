package com.victoryw.deplayqueue.redis.`interface`


import IntegrationTestBase
import com.victoryw.deplayqueue.redis.fake.gateway.interfaces.FakeDelayJobConsumer
import com.victoryw.deplayqueue.redis.interfaces.DelayJob
import com.victoryw.deplayqueue.redis.interfaces.DelayJobWorker
import com.victoryw.deplayqueue.redis.interfaces.IDelayJobConsumer
import com.victoryw.deplayqueue.redis.interfaces.QueueOperator
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.Assert
import java.util.*

class DelayJobListenerTest(
        @Autowired val delayJobWorker: DelayJobWorker,
        @Autowired redisQueueOperator: QueueOperator,
        @Autowired delayJobConsumer: IDelayJobConsumer
) : IntegrationTestBase(redisQueueOperator, "source") {

    val delayJobConsumer = delayJobConsumer as FakeDelayJobConsumer;

    @Test
    fun should_get_the_delay_job_on_time() {
        val advancedMilliSeconds = 5000
        val current = Date().time
        val delayRequest = DelayJob(current - advancedMilliSeconds, queueType)
        redisQueueOperator.createDelayJobs(delayRequest)
        delayJobWorker.runOnce(queueType, current)
        Assert.isTrue(delayJobConsumer.testCallJob(delayRequest))
    }

    @Test
    fun should_not_get_the_delay_job_after_time() {
        val advancedMilliSeconds = 1
        val current = Date().time
        val delayRequest = DelayJob(current + advancedMilliSeconds, queueType)
        redisQueueOperator.createDelayJobs(delayRequest)
        delayJobWorker.runOnce(queueType, current)
        Assert.isTrue(!delayJobConsumer.testCallJob(delayRequest))
    }


}