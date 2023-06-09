package com.example.paymentsv2.service

import com.example.paymentsv2.dtos.UserModel
import com.example.paymentsv2.models.RolesEnums
import com.example.paymentsv2.models.User

interface UserService {
    fun saveUser(userDto: UserModel?, role: RolesEnums? = RolesEnums.ROLE_USER)
    fun findByEmail(email: String?): User?
    fun findAllUsers(): List<UserModel?>?
}