package com.bstsnail.rabbitmq.topic

import com.bstsnail.rabbitmq.utils.RabbitUtils
import com.rabbitmq.client.BuiltinExchangeType

class EmitTopicLog {

    fun emit(routing: String) {
        val connection = RabbitUtils.connection()
        val channel = connection.createChannel()

        channel.exchangeDeclare("topic_logs", BuiltinExchangeType.TOPIC)

        val message = "This is topic log message"

        channel.basicPublish("topic_logs", routing, null, message.toByteArray())

        println(" [x] Sent '$message' with routing '$routing'")

        channel.close()
        connection.close()
    }
}

fun main(argv: Array<String>) {
    EmitTopicLog().emit("test.wrong")
}