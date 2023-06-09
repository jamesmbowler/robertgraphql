package com.example.paymentsv2.controllers

import com.example.paymentsv2.inputs.OrderItemInput
import com.example.paymentsv2.models.OrderItems
import com.example.paymentsv2.models.Orders
import com.example.paymentsv2.models.User
import com.example.paymentsv2.repositories.MenuItemRepository
import com.example.paymentsv2.repositories.OrderRepository
import com.example.paymentsv2.repositories.UserRepository
import com.example.paymentsv2.robert.utils.OrderStatus
import com.example.paymentsv2.service.NotificationService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails

@DgsComponent
class OrderMutations @Autowired constructor(
    var orderRepository: OrderRepository,
    var userRepository: UserRepository,
    var menuItemRepository: MenuItemRepository,
    var notificationService: NotificationService
) {

    //@Transactional
    @DgsMutation
    //@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    fun createOrder(
        @InputArgument order: List<OrderItemInput>,
        @AuthenticationPrincipal userDetails: UserDetails
    ): Orders {
        val user: User? = userRepository.findByEmail(userDetails.username)

        val menuItems = menuItemRepository.findAllById(order.map { it.menuItemId })

        val newOrder = orderRepository.save(Orders(
            id = null,
            isActive = true,
            name = null,
            status = OrderStatus.NEW,
            total = order.sumOf { orderItemInput ->
                menuItems.find { it?.id == orderItemInput.menuItemId }?.price!!
            },
            items = null,
            userId = user?.id
        ))
        newOrder.user = setOf(user)

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
        val fcmToken: String? =  userRepository.findByIdOrNull(order?.userId!!)?.fcmToken
        if (fcmToken?.isNotEmpty() == true) {
            notificationService.sendNotification(fcmToken, orderId)
        }
        return orderRepository.save(order)
    }

}