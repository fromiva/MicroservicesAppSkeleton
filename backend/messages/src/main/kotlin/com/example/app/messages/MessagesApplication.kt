package com.example.app.messages

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class MessagesApplication

fun main(args: Array<String>) {
	runApplication<MessagesApplication>(*args)
}
