package com.example.paymentsv2.dtos

import com.example.paymentsv2.models.Role

data class UserDto (
    var id: Long? = null,
    var name: String? = null,
    var email: String? = null,
    var roles: List<Role> = ArrayList(),
)