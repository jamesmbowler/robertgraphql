package com.example.paymentsv2.repositories

import com.example.paymentsv2.models.Department
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository


@Repository
interface DepartmentRepository : JpaRepository<Department, Long>, JpaSpecificationExecutor<Department>