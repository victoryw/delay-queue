package com.victoryw.deplayqueue.redis.gateway.controller

import com.victoryw.deplayqueue.redis.interfaces.DelayJob
import com.victoryw.deplayqueue.redis.interfaces.QueueOperator
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/delayJobs")
class DelayJobController(private val queueOperator: QueueOperator) {
    @GetMapping("/")
    fun getAll() = listOf("1");

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody delayRequest: DelayJob) {
        queueOperator.createDelayJobs(delayRequest)
    }

}

