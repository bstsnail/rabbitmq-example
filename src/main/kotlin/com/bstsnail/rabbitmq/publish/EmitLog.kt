package com.bstsnail.rabbitmq.publish

import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.ConnectionFactory

class EmitLog {

    fun emit() {
        val factory = ConnectionFactory()
        factory.host = "127.0.0.1"
        factory.port = 5672
        val connection = factory.newConnection()
        val channel = connection.createChannel()

        channel.exchangeDeclare("logs", BuiltinExchangeType.FANOUT)

        val message = "This is a log"
        channel.basicPublish("logs", "", null, message.toByteArray())
        println(" [x] Send '$message'")
        channel.close()
        connection.close()
    }
}

fun main(argv: Array<String>) {
    EmitLog().emit()
}