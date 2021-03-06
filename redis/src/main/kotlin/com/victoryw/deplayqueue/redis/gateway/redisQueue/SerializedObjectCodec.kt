package com.victoryw.deplayqueue.redis.gateway.redisQueue

import com.victoryw.deplayqueue.redis.interfaces.DelayJob
import io.lettuce.core.codec.RedisCodec
import java.nio.ByteBuffer
import java.io.*


class SerializedObjectCodec: RedisCodec<String, DelayJob> {
    private val charset = Charsets.UTF_8;

    override fun decodeKey(bytes: ByteBuffer?): String {
        return  charset.decode(bytes).toString();
    }

    override fun decodeValue(bytes: ByteBuffer?): DelayJob? {
        if(bytes == null) {
            return  null;
        }

        val array = ByteArray(bytes.remaining())
        bytes.get(array);
        val inputStream = ObjectInputStream(ByteArrayInputStream(array))
        inputStream.use {
            return it.readObject() as? DelayJob
        }
    }

    override fun encodeValue(value: DelayJob?): ByteBuffer? {
        val bytes = ByteArrayOutputStream()
        ObjectOutputStream(bytes).use  {
            it.writeObject(value)
            return ByteBuffer.wrap(bytes.toByteArray())
        }
    }

    override fun encodeKey(key: String?): ByteBuffer {
        return charset.encode(key)
    }



}