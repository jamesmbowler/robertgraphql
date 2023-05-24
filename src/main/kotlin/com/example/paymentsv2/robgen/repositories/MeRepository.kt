package com.example.paymentsv2.robgen.repositories

import com.example.paymentsv2.models.User
import kotlin.Long
import org.springframework.`data`.jpa.repository.JpaRepository
import org.springframework.`data`.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
public interface MeRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User>
