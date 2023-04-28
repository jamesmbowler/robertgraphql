package com.example.paymentsv2.robert.utils

enum class IntFilterOperators(override val value: String): FilterOperator {
    EQ("eq"),
    GT("gt"),
    GE("ge"),
    LT("lt"),
    LE("le")
}