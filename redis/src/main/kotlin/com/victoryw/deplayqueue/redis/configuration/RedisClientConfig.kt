package com.victoryw.deplayqueue.redis.configuration

import io.lettuce.core.RedisClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisClientConfig{
    @Bean fun createClient(): RedisClient {
        return RedisClient.create("redis://localhost:6380/1");
    }
}