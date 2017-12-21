package com.bstsnail.rabbitmq.soda

import com.bstsnail.rabbitmq.utils.RabbitUtils.sodaConnection
import java.util.HashMap
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory


class DelayLog {

    fun emit(routing: String) {
        val connection = sodaConnection()
        val channel = connection.createChannel()

        /*
        val args = HashMap<String, Any>()
        args.put("x-delayed-type", "direct")
        channel.exchangeDeclare("my-exchange", "x-delayed-message", true, false, args)
        */
        val message = "This is topic log message"

        val headers = HashMap<String, Any>()
        headers.put("x-delay", 0)
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