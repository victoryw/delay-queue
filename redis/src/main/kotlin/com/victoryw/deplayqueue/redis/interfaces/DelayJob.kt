package com.victoryw.deplayqueue.redis.interfaces

import java.io.Serializable

data class DelayJob(
        val delayTo: Long,
        val sourceType: String
) : Serializable