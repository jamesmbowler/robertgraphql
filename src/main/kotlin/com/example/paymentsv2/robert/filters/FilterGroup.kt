package com.example.paymentsv2.robert.filters

public data class FilterGroup<T>(
    val filter: List<T>? = listOf(),
    val operator: String = "AND"
)