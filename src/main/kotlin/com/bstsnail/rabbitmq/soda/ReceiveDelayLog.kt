package com.bstsnail.rabbitmq.soda

import com.bstsnail.rabbitmq.utils.RabbitUtils.sodaConnection
import com.rabbitmq.client.*
import java.util.HashMap

class ReceiveDelayLog {

    fun receive(routing: String) {
        val connection = sodaConnection()
        val channel = connection.createChannel()

        val args = HashMap<String, Any>()
        args.put("x-delayed-type", "direct")
        channel.exchangeDeclare("my-exchange", "x-delayed-message", true, false, args)

        //channel.exchangeDeclare("topic_logs", BuiltinExchangeType.TOPIC)
        val queueName = channel.queueDeclare().queue

        channel.queueBind(queueName, "my-exchange", routing)

        println(" [*] Waiting for messages of queue $queueName")

        val consumer = object: DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray) =
                    println("${System.currentTimeMillis()}: Receive '${String(body)}'")
        }

        channel.basicConsume(queueName, true, consumer)
    }
}

fun main(argv: Array<String>) {
    ReceiveDelayLog().receive("test")
}