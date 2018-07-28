package com.victoryw.deplayqueue.redis.fake.configuration

import com.victoryw.deplayqueue.redis.configuration.RedisQueueEnableSolution
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class FakeRedisQueueMonitorConfig {
    @Bean
    @Primary
    fun createFakeRedisQueueEnabled(): RedisQueueEnableSolution {
        return FakeRedisQueueEnableSolution();
    }
}

class FakeRedisQueueEnableSolution: RedisQueueEnableSolution() {
    override fun isEnabled(): Boolean {
        return false;
    }
}