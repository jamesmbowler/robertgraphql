package com.example.paymentsv2.inputs

import java.math.BigDecimal

data class NewMenuItemInput(
    val isActive: Boolean? = true,
    val soldOut: Boolean? = false,
    val name: String?,
    val description: String?,
    val price: BigDecimal?,
    val menuId: Long?
)