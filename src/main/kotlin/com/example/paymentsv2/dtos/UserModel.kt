package com.example.paymentsv2.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class UserModel (
    private val id: Long? = null,

    @NotEmpty
    val firstName: String? = null,

    @NotEmpty
    val lastName: String? = null,

    @NotEmpty(message = "Email should not be empty")
    @Email
    val email: String? = null,

    @NotEmpty(message = "Password should not be empty")
    val password: String? = null
)