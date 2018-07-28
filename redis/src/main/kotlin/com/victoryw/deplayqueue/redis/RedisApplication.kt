package com.victoryw.deplayqueue.redis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
open class RedisApplication

fun main(args: Array<String>) {
    runApplication<RedisApplication>(*args)
}

