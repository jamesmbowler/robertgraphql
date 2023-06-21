package com.example.paymentsv2.repositories

import com.example.paymentsv2.models.Orders
import org.springframework.data.jpa.repository.JpaRepository


interface OrderRepository : JpaRepository<Orders?, Long?>
