package com.victoryw.deplayqueue.redis.gateway.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest
class OrderControllerTest(@Autowired private val mockMvc: MockMvc) {
    @Test
    fun `should get the 200 ok after call order api`() {
        mockMvc.perform(get("/api/orders/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }
}