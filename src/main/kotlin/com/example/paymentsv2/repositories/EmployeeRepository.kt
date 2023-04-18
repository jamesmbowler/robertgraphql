package com.example.paymentsv2.repositories

import com.example.paymentsv2.models.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>