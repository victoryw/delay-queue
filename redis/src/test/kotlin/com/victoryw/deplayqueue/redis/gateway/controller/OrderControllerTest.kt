package com.victoryw.deplayqueue.redis.gateway.controller

import ApiTestBase
import com.fasterxml.jackson.databind.ObjectMapper
import com.victoryw.deplayqueue.redis.interfaces.DelayJob
import com.victoryw.deplayqueue.redis.gateway.redisQueue.RedisQueueOperator
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.Assert
import org.springframework.web.context.WebApplicationContext
import java.time.Instant
import kotlin.test.assertEquals


class OrderControllerTest @Autowired constructor(
        wac: WebApplicationContext,
        objectMapper: ObjectMapper,
        redisQueueOperator: RedisQueueOperator
) : ApiTestBase(wac, "source", redisQueueOperator, objectMapper) {

    private val resourceUrl = "/api/delayJobs/"


    @Test
    fun `should get the 200 ok after call order api`() {
        mockMvc.perform(get(resourceUrl).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk)
    }



    @Test
    fun `should create a delayed event in the queue`() {
        val minute = 6000L
        val plusSeconds = Instant.now().plusSeconds(minute)
        val eventData = DelayJob(plusSeconds.toEpochMilli(),
                queueType)

        mockMvc.perform(post(resourceUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(eventData))).andExpect(status().isCreated)

        val delayJobs = redisQueueOperator.fetchMembers(eventData.sourceType);

        Assert.notEmpty(delayJobs)

        assertEquals(1, delayJobs.size)

    }
}