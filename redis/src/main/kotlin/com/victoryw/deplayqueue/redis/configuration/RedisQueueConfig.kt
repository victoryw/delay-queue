package com.victoryw.deplayqueue.redis.configuration

import com.victoryw.deplayqueue.redis.gateway.redisQueue.DelayQueueRedisKeyBuilder
import com.victoryw.deplayqueue.redis.gateway.redisQueue.RedisQueueOperator
import com.victoryw.deplayqueue.redis.interfaces.IDelayQueueKeyBuilder
import com.victoryw.deplayqueue.redis.interfaces.QueueOperator
import io.lettuce.core.RedisClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisQueueConfig {
    @Bean
    fun createRedisQueueKey(): IDelayQueueKeyBuilder {
        return DelayQueueRedisKeyBuilder()
    }

    @Bean
    fun createRedisQueueOperator(redisClient: RedisClient,
                                 delayQueueRedisKeyBuilder: IDelayQueueKeyBuilder): QueueOperator {
        return RedisQueueOperator(redisClient, delayQueueRedisKeyBuilder)
    }


}