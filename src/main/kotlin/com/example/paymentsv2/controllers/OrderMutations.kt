package com.example.paymentsv2.controllers

import com.example.paymentsv2.inputs.OrderItemInput
import com.example.paymentsv2.models.OrderItems
import com.example.paymentsv2.models.Orders
import com.example.paymentsv2.repositories.MenuItemRepository
import com.example.paymentsv2.repositories.OrderRepository
import com.example.paymentsv2.robert.utils.OrderStatus
import com.example.paymentsv2.service.NotificationService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class OrderMutations @Autowired constructor(
    var orderRepository: OrderRepository,
    var menuItemRepository: MenuItemRepository,
    var notificationService: NotificationService
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

    @DgsMutation
    fun updateOrder(@InputArgument orderId: Long, status: OrderStatus): Orders {
        val order = orderRepository.findById(orderId)
            .orElseThrow { throw IllegalArgumentException("Menu item not found with ID: $orderId") }

        order?.status = status

        val phoneId = "dTySCQ2BSEikWdjfEnJEoD:APA91bHYRPK9erbaNea9KEt8lcR8Z-gdm_4W-PUwf57c4vIbxSNBU7PI8Ip-isSJ7SXhlS1VA1DJWyVTQg_Kj8FOqgCdpBpD12WJ5eu-8BvD987H4Jt7I05nUXG7Sf3uNr6u9Q61isg9"

        val eId = "frT6IWvRRkyx1DpdZTLyow:APA91bH9YeZ7at55x0krQPMFIH8T-eVYnwbLo4ZnHkwgIaw1RqWuMd33YPVf856cHYBybrddYZFE_1szrMIxqeYkZfcYQqJH5zSXNHabXe0NgL10fW7JlbgnPE3TZOQDJ0-qGGE4JAJv"
        //notificationService.sendNotification(eId)
        notificationService.sendNotification(phoneId)
        return orderRepository.save(order!!)
    }

}