package com.bstsnail.rabbitmq.helloworld

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Envelope


class Consumer {

    fun receive() {
        val factory = ConnectionFactory()
        factory.host = "127.0.0.1"
        factory.port = 5672
        val connection = factory.newConnection()
        val channel = connection.createChannel()

        channel.queueDeclare("hello", false, false, false, null)
        println(" [*] Waiting for messages")

        val consumer = object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String, envelope: Envelope,
                                        properties: AMQP.BasicProperties, body: ByteArray) {
                val message = String(body)
                println(" [x] Received '$message'")
            }
        }

        channel.basicConsume("hello", true, consumer)

    }
}

fun main(argv: Array<String>) {
    Consumer().receive()
}