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

    fun sodaConnection(): Connection {
        val factory = ConnectionFactory()
        factory.host = "10.125.48.80"
        factory.port = 5672
        factory.username = "..."
        factory.password = "..."
        factory.virtualHost = "soda34"
        return factory.newConnection()
    }
}