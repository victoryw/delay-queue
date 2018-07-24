package com.victoryw.deplayqueue.redis.fake.gateway.interfaces

import com.victoryw.deplayqueue.redis.interfaces.DelayJob
import com.victoryw.deplayqueue.redis.interfaces.IDelayJobConsumer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

class FakeDelayJobConsumer : IDelayJobConsumer {
    private var testJob: DelayJob? = null

    fun testCallJob(delayJob: DelayJob): Boolean {
        return this.testJob == delayJob;
    }

    override fun consume(delayJob: DelayJob) {
        this.testJob = delayJob;
    }
}

@TestConfiguration
class FakeDelayJobConfig {
    @Primary
    @Bean
    fun createFakeDelayJobConsumer(): IDelayJobConsumer {
        return FakeDelayJobConsumer();
    }
}