package com.example.paymentsv2.controllers

import com.example.paymentsv2.inputs.MenuUpdateData
import com.example.paymentsv2.inputs.NewMenuItemInput
import com.example.paymentsv2.models.MenuItems
import com.example.paymentsv2.repositories.MenuItemRepository
import com.example.paymentsv2.repositories.MenuRepository
import com.example.paymentsv2.service.NotificationService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class MenuMutations @Autowired constructor(
    var menuRepository: MenuRepository,
    var menuItemRepository: MenuItemRepository,
    var notificationService: NotificationService
) {
    @DgsMutation
    fun createMenuItem(@InputArgument menuItem: NewMenuItemInput): MenuItems {
        val menu = menuRepository.getMenuWithItems(menuItem.menuId!!)

        val newMenuItem = MenuItems(
            id = null,
            isActive = menuItem.isActive,
            soldOut = menuItem.soldOut,
            name = menuItem.name,
            description = menuItem.description,
            price = menuItem.price,
            //menus = setOf(menu!!) as Set<Menus>
        )
        //newMenuItem.menus?.plus(menu)

        val savedMenuItem = menuItemRepository.save(newMenuItem)

        //Hibernate.initialize(menu?.menuItems)
        val menuItems = menu?.menuItems?.plus(savedMenuItem)
        //menuItems?.plus(savedMenuItem)
        // Update the inverse side of the relationship in the menu object
        menu?.menuItems = menuItems

        menuRepository.save(menu!!) // Save the updated menu object

        return savedMenuItem
    }

    @DgsMutation
    fun updateMenuItem(@InputArgument menuItemId: Long, data: MenuUpdateData): MenuItems {
        val menuItem = menuItemRepository.findById(menuItemId)
            .orElseThrow { throw IllegalArgumentException("Menu item not found with ID: $menuItemId") }

        if (data.quantity != null)
            menuItem?.quantity = data.quantity

        if (data.name != null)
            menuItem?.name = data.name

        if (data.price != null)
            menuItem?.price = data.price.toBigDecimal()

        return menuItemRepository.save(menuItem!!)
    }
}