package com.example.paymentsv2.repositories

import com.example.paymentsv2.models.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role?, Long?> {
    //fun findByName(name: RolesEnums?): Role?
    fun findByName(name: String?): Role?
}
