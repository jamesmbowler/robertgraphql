package com.example.paymentsv2.robert.utils

interface FilterOperator {
    val value: String
}

enum class StringFilterOperators(override val value: String): FilterOperator {
    EQ("eq"),
    ENDS_WITH("endsWith"),
    STARTS_WITH("startsWith"),
    CONTAINS("contains")
}