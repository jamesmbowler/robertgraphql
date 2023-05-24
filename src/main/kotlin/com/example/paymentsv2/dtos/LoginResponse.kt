package com.example.paymentsv2.dtos

data class LoginResponse(
    val user: UserDto,
    val venues: ArrayList<LinkedHashMap<Any,Any>>
)
