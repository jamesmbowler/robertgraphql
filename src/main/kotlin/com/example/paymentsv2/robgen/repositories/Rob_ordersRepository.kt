package com.example.paymentsv2.robgen.repositories

import com.example.paymentsv2.models.Orders
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
public interface Rob_ordersRepository : JpaRepository<Orders, Long>,
    JpaSpecificationExecutor<Orders>
