package com.example.paymentsv2.controllers

import com.example.paymentsv2.generated.types.OrderItemInput
import com.example.paymentsv2.models.Orders
import com.example.paymentsv2.service.QueryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrdersController(
    @Autowired val queryService: QueryService
) {

    @PostMapping("order")
    fun test(@RequestBody order: List<OrderItemInput>): Orders {
        return queryService.orderQuery(order)
    }
}