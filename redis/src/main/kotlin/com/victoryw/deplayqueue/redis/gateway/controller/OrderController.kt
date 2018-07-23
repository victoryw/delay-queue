package com.victoryw.deplayqueue.redis.gateway.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.victoryw.deplayqueue.redis.gateway.redisQueue.DelayJobDTO
import com.victoryw.deplayqueue.redis.gateway.redisQueue.DelayQueueRedisKeyBuilder
import com.victoryw.deplayqueue.redis.interfaces.IDelayQueueKeyBuilder
import io.lettuce.core.RedisClient
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(private val redisClient: RedisClient, private val objectMapper: ObjectMapper,
                      private val keyBuilder: IDelayQueueKeyBuilder) {
    @GetMapping("/")
    fun getAll() = listOf("1");

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED)
    fun create(@RequestBody delayRequest: DelayJobDTO) {
        this.redisClient.connect().use { connect ->
            val redisCommando = connect.sync();
            val redisKey = DelayQueueRedisKeyBuilder().createDelayQueueKey(delayRequest.sourceType)
            redisCommando.zadd(redisKey, delayRequest.delayTo.toDouble(), objectMapper.writeValueAsString(delayRequest));
        }
    }

}

