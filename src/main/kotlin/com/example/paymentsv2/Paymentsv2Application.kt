package com.example.paymentsv2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan
class Paymentsv2Application

fun main(args: Array<String>) {
	runApplication<Paymentsv2Application>(*args)
}
