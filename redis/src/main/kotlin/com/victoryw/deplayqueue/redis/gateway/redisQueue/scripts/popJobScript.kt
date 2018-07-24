package com.victoryw.deplayqueue.redis.gateway.redisQueue.scripts

const val popJobScript = """
local queueKey = KEYS[1]
local now = KEYS[2]

local payload = nil
local i, lPayload = next(redis.call('ZRANGEBYSCORE', queueKey, '-inf', now, 'LIMIT' , '0' , '1'))
if lPayload then
    payload = lPayload
    redis.call('ZREM', queueKey, payload)
end
return payload
"""