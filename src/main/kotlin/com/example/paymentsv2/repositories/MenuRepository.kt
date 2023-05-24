package com.example.paymentsv2.repositories

import com.example.paymentsv2.models.Menus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MenuRepository : JpaRepository<Menus?, Long?> {
    @Query("SELECT m FROM Menus m LEFT JOIN FETCH m.menuItems WHERE m.id = :menuId")
    fun getMenuWithItems(@Param("menuId") menuId: Long): Menus?
}