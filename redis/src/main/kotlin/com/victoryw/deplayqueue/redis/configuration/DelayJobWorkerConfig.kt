package com.victoryw.deplayqueue.redis.configuration

import com.victoryw.deplayqueue.redis.interfaces.DelayJobWorker
import com.victoryw.deplayqueue.redis.interfaces.EmptyDelayJobConsumer
import com.victoryw.deplayqueue.redis.interfaces.IDelayJobConsumer
import com.victoryw.deplayqueue.redis.interfaces.QueueOperator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DelayJobWorkerConfig {
    @Bean
    fun createDelayJobConsumer():IDelayJobConsumer{
        return EmptyDelayJobConsumer();
    }


    @Bean
    fun createWorker(queueOperator: QueueOperator, delayJobConsumer: IDelayJobConsumer): DelayJobWorker {
        return DelayJobWorker(queueOperator, delayJobConsumer);
    }
}