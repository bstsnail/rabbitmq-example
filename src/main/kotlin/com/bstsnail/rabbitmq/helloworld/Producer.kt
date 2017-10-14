package com.bstsnail.rabbitmq.helloworld

import com.rabbitmq.client.ConnectionFactory

class Producer {

    fun send() {
        val factory = ConnectionFactory()
        factory.host = "127.0.0.1"
        factory.port = 5672
        val connection = factory.newConnection()
        val channel = connection.createChannel()

        channel.queueDeclare("hello", false, false, false, null)
        val message = "Hello World"
        channel.basicPublish("", "hello", null, message.toByteArray())
        println(" [x] Send '$message'")
        channel.close()
        connection.close()
    }
}

fun main(argv: Array<String>) {
    Producer().send()
}