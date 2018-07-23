package com.victoryw.deplayqueue.redis.gateway.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.victoryw.deplayqueue.redis.gateway.redisQueue.RedisQueueOperator
import com.victoryw.deplayqueue.redis.interfaces.IDelayQueueKeyBuilder
import io.lettuce.core.RedisClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.Assert
import org.springframework.web.context.WebApplicationContext
import java.time.Instant
import kotlin.test.assertEquals

@ExtendWith(SpringExtension::class)
@SpringBootTest
class OrderControllerTest @Autowired constructor(private val wac: WebApplicationContext,
                                                 private val objectMapper: ObjectMapper,
                                                 val redisClient: RedisClient,
                                                 private val keyBuilder: IDelayQueueKeyBuilder,
                                                 private val redisQueueOperator: RedisQueueOperator) {

    private lateinit var mockMvc: MockMvc
    private lateinit var resourceUrl: String
    private val queueType = "source"

    @BeforeEach
    fun baseBefore() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
        redisQueueOperator.deleteQueue(queueType)
        resourceUrl  = "/api/delayJobs/"
    }



    @Test
    fun `should get the 200 ok after call order api`() {
        mockMvc.perform(get(resourceUrl).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk)
    }



    @Test
    fun `should create a delayed event in the queue`() {
        val minute = 6000L
        val plusSeconds = Instant.now().plusSeconds(minute)
        val eventData = DelayJobDTO(plusSeconds.toEpochMilli(),
                "testEvent", queueType)

        mockMvc.perform(post(resourceUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(eventData))).andExpect(status().isCreated)

        val delayJobs = redisQueueOperator.fetchQueueMembers(eventData.sourceType);

        Assert.notEmpty(delayJobs)

        assertEquals(1, delayJobs!!.size)
    }
}

