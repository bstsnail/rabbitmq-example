package com.bstsnail.rabbitmq.routing

import com.bstsnail.rabbitmq.utils.RabbitUtils
import com.rabbitmq.client.BuiltinExchangeType

class EmitDirctLogs {

    fun emit(severity: String) {
        val connection = RabbitUtils.connection()
        val channel = connection.createChannel()

        channel.exchangeDeclare("direct_logs", BuiltinExchangeType.DIRECT)

        val message = "This is a direct log message of severity<$severity>"
        channel.basicPublish("direct_logs", severity, null, message.toByteArray())
        println(" [x] Send '$message'")
        channel.close()
        connection.close()
    }
}

fun main(argv: Array<String>) {
    EmitDirctLogs().emit("info")
}