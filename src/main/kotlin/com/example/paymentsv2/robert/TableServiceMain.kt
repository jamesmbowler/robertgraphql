package com.example.paymentsv2.robert

import com.example.paymentsv2.Paymentsv2Application
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext

class TableServiceMain {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val context: ApplicationContext = SpringApplication.run(Paymentsv2Application::class.java, *args)
            val tableService: TableService = context.getBean(TableService::class.java)
            tableService.getAllEntities()
            System.exit(0)
        }
    }
}
