package com.example.paymentsv2.service

import com.example.paymentsv2.dtos.UserModel
import com.example.paymentsv2.models.User

interface UserService {
    fun saveUser(userDto: UserModel?)
    fun findByEmail(email: String?): User?
    fun findAllUsers(): List<UserModel?>?
}