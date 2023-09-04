package br.com.client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClientAppApplication

fun main(args: Array<String>) {
	runApplication<ClientAppApplication>(*args)
}
