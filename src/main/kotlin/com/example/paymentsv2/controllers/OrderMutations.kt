package com.example.paymentsv2.controllers

import com.example.paymentsv2.inputs.OrderItemInput
import com.example.paymentsv2.models.OrderItems
import com.example.paymentsv2.models.Orders
import com.example.paymentsv2.repositories.MenuItemRepository
import com.example.paymentsv2.repositories.OrderRepository
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class OrderMutations @Autowired constructor(
    var orderRepository: OrderRepository,
    var menuItemRepository: MenuItemRepository
) {

    @DgsMutation
    fun createOrder(@InputArgument order: List<OrderItemInput>): Orders {

        val menuItems = menuItemRepository.findAllById(order.map { it.menuItemId })

        val newOrder = orderRepository.save(Orders(
            id = null,
            isActive = true,
            name = null,
            total = order.sumOf { orderItemInput ->
                menuItems.find { it?.id == orderItemInput.menuItemId }?.price!!
            },
            items = null
        ))

        newOrder.items = order.map { it ->
            OrderItems(
                id = null,
                isActive = true,
                comments = it.comments,
                menuItemId = it.menuItemId,
                orders = listOf(newOrder),
                menuItem = menuItems.firstOrNull { item -> item?.id == it.menuItemId }?.toDto()
            ) }.toList()

        val ret = orderRepository.save(newOrder)

        return ret
    }

}