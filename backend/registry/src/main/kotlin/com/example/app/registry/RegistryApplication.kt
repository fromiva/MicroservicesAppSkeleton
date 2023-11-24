package com.example.app.registry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
open class RegistryApplication

fun main(args: Array<String>) {
	runApplication<RegistryApplication>(*args)
}
