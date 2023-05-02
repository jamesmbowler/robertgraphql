package com.example.paymentsv2.robgen.repositories

import com.example.paymentsv2.models.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface Rob_addressesRepository : JpaRepository<Address, Long>,
    JpaSpecificationExecutor<Address>
