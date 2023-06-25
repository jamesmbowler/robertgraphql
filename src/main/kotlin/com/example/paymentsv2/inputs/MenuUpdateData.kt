package com.example.paymentsv2.inputs

data class MenuUpdateData(
    val isActive: Boolean? = null,
    val soldOut: Boolean? = null,
    val quantity: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val menuId: Long? = null
)