package com.victoryw.deplayqueue.redis.gateway.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.victoryw.deplayqueue.redis.gateway.redisQueue.DelayJobDTO
import com.victoryw.deplayqueue.redis.gateway.redisQueue.RedisQueueOperator
import com.victoryw.deplayqueue.redis.interfaces.IDelayQueueKeyBuilder
import io.lettuce.core.RedisClient
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/delayJobs")
class DelayJobController(private val redisClient: RedisClient, private val objectMapper: ObjectMapper,
                         private val keyBuilder: IDelayQueueKeyBuilder,
                         private val redisQueueOperator: RedisQueueOperator) {
    @GetMapping("/")
    fun getAll() = listOf("1");

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED)
    fun create(@RequestBody delayRequest: DelayJobDTO) {
        redisQueueOperator.createDelayJobs(delayRequest)
    }

}

