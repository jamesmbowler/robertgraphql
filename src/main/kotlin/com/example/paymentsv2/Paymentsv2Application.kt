package com.example.paymentsv2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

@SpringBootApplication
@ComponentScan(basePackages = ["com.example.paymentsv2"])
@EnableJpaRepositories
@EnableMethodSecurity(securedEnabled = true)
class Paymentsv2Application

//@Autowired
//private lateinit var tableService: TableService
//
//@Value("\${tableservice.run-on-startup:true}")
//private val runOnStartup: Boolean = true

//@PostConstruct
//fun onStartup() {
//	if (runOnStartup) {
//		tableService.getAllEntities()
//	}
//}

fun main(args: Array<String>) {

	runApplication<Paymentsv2Application>(*args)
}
