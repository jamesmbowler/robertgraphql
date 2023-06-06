package com.example.paymentsv2.utils

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class AppStartRunner : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        println("testing")
    }
}