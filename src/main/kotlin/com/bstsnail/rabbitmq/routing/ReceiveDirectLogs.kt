package com.bstsnail.rabbitmq.routing

import com.bstsnail.rabbitmq.utils.RabbitUtils
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope

class ReceiveDirectLogs {

    fun receive(severities: Array<String>) {

        val connection = RabbitUtils.connection()
        val channel = connection.createChannel()

        channel.exchangeDeclare("direct_logs", BuiltinExchangeType.DIRECT)
        val queueName = channel.queueDeclare().queue
        severities.forEach {
            channel.queueBind(queueName, "direct_logs", it)
        }

        println(" [*] Waiting for messages of queue $queueName")

        val consumer = object: DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String,
                                        envelope: Envelope,
                                        properties: AMQP.BasicProperties,
                                        body: ByteArray) =
                    println("Receive '${String(body)}'")
        }
        channel.basicConsume(queueName, true, consumer)
    }
}

fun main(argv: Array<String>) {
    ReceiveDirectLogs().receive(arrayOf("info"))
}