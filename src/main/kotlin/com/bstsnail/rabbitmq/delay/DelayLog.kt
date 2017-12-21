package com.bstsnail.rabbitmq.delay

import com.bstsnail.rabbitmq.utils.RabbitUtils
import com.rabbitmq.client.BuiltinExchangeType
import java.util.HashMap
import com.rabbitmq.client.AMQP





class DelayLog {

    fun emit(routing: String) {
        val connection = RabbitUtils.connection()
        val channel = connection.createChannel()

        val args = HashMap<String, Any>()
        args.put("x-delayed-type", "direct")
        channel.exchangeDeclare("my-exchange", "x-delayed-message", true, false, args)

        val message = "This is topic log message"

        val headers = HashMap<String, Any>()
        headers.put("x-delay", 5000)
        val props = AMQP.BasicProperties.Builder().headers(headers)
        channel.basicPublish("my-exchange", routing, props.build(), message.toByteArray())

        println("${System.currentTimeMillis()}: [x] Sent '$message' with routing '$routing'")

        Thread.sleep(10000)
        channel.close()
        connection.close()
    }
}

fun main(argv: Array<String>) {
    DelayLog().emit("test")
}