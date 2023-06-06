package com.example.paymentsv2.controllers

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
    fun updateSoldOut(@InputArgument menuItemId: Long, soldOut: Boolean): MenuItems {
        val menuItem = menuItemRepository.findById(menuItemId)
            .orElseThrow { throw IllegalArgumentException("Menu item not found with ID: $menuItemId") }

        menuItem?.soldOut = soldOut
        val phoneId = "eNh56KquSHu_Xf6mZfzOBk:APA91bEoIB4A1rVurvDQhv1spDTeGLbtEU6NCpyieFWlpEdzegXgQH1u4fyzdT3rwdfB4elSEokAkdQAI96MNP4gK6lAHLZllqzo9hlLOX9xSybSce6oSGE6XHERpFOK4dd3ICt-oOqE"
        val eId = "frT6IWvRRkyx1DpdZTLyow:APA91bH9YeZ7at55x0krQPMFIH8T-eVYnwbLo4ZnHkwgIaw1RqWuMd33YPVf856cHYBybrddYZFE_1szrMIxqeYkZfcYQqJH5zSXNHabXe0NgL10fW7JlbgnPE3TZOQDJ0-qGGE4JAJv"
        //notificationService.sendNotification(eId)
        notificationService.sendNotification(phoneId)
        return menuItemRepository.save(menuItem!!)
    }
}