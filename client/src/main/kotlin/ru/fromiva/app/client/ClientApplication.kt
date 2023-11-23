package ru.fromiva.app.client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
open class ClientApplication

fun main(args: Array<String>) {
	runApplication<ClientApplication>(*args)
}
