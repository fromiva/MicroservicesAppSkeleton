package com.example.app.users

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
open class UserApplication

fun main(args: Array<String>) {
	runApplication<UserApplication>(*args)
}
