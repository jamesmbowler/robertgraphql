package com.example.paymentsv2.repositories

import com.example.paymentsv2.models.MenuItems
import org.springframework.data.jpa.repository.JpaRepository

interface MenuItemRepository : JpaRepository<MenuItems?, Long?>
