package com.bstsnail.rabbitmq.publish

import com.bstsnail.rabbitmq.utils.RabbitUtils
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope

class ReceiveLogs {

    fun receive() {
        val connection = RabbitUtils.connection()
        val channel = connection.createChannel()

        channel.exchangeDeclare("logs", BuiltinExchangeType.FANOUT)
        val queueName = channel.queueDeclare().queue
        channel.queueBind(queueName, "logs", "")

        println(" [*] Waiting for messages of queue '$queueName'")

        val consumer = object: DefaultConsumer(channel) {

            override fun handleDelivery(consumerTag: String,
                                        envelope: Envelope,
                                        properties: AMQP.BasicProperties,
                                        body: ByteArray) =
                    println(" [x] Received '${String(body)}'")
        }
        channel.basicConsume(queueName, true, consumer)
    }
}

fun main(argv: Array<String>) {
    ReceiveLogs().receive()
}