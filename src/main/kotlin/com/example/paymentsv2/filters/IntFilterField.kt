package com.example.paymentsv2.filters

import org.springframework.stereotype.Component

@Component
class IntFilterField: FilterField(type = Int::class.java)