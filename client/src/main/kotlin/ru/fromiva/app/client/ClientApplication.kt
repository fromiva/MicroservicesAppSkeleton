package ru.fromiva.app.client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ClientApplication

fun main(args: Array<String>) {
	runApplication<ClientApplication>(*args)
}
