package com.bstsnail.rabbitmq.topic

import com.bstsnail.rabbitmq.utils.RabbitUtils
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope

class ReceiveTopLog {

    fun receive(routing: String) {
        val connection = RabbitUtils.connection()
        val channel = connection.createChannel()

        channel.exchangeDeclare("topic_logs", BuiltinExchangeType.TOPIC)
        val queueName = channel.queueDeclare().queue

        channel.queueBind(queueName, "topic_logs", routing)

        println(" [*] Waiting for messages of queue $queueName")

        val consumer = object: DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray) =
                    println(" Receive '${String(body)}'")
        }

        channel.basicConsume(queueName, true, consumer)
    }
}

fun main(argv: Array<String>) {
    ReceiveTopLog().receive("*.wrong")
}