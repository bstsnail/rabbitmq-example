package com.bstsnail.rabbitmq.utils

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory

object RabbitUtils {

    fun connection(): Connection {
        val factory = ConnectionFactory()
        factory.host = "127.0.0.1"
        factory.port = 5672
        return factory.newConnection()
    }
}