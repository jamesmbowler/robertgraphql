package com.example.paymentsv2.robert.filters

import org.springframework.stereotype.Component
import kotlin.reflect.full.declaredMemberProperties

@Component
open class Filter {
    fun getFilters(): MutableList<FilterField> {
        return this.javaClass.kotlin.declaredMemberProperties
            .filter { property ->
                val value = property.get(this)
                (value is FilterField) && (value.value != null)
            }
            .map { property ->
                val value = property.get(this)
                if (value is FilterField) {
                    value.name = property.name
                }
                value
            }
            .filterIsInstance<FilterField>()
            .toMutableList()
    }

    fun createFilterField(filterArguments: Map<String, String>?, fieldType: Class<*>, name: String?): FilterField? {
        if (filterArguments != null) {
            val operator = filterArguments["operator"] as String
            val value = filterArguments["value"] as String
            val queryOperator = QueryOperator.valueOf((filterArguments["queryOperator"] as String).toUpperCase())
            return when (fieldType) {
                IntFilterField::class.java -> IntFilterField(value = value, operator = operator, name = name)
                FilterField::class.java -> FilterField(value = value, operator = operator)
                else -> null
            }
        }
        return null
    }
}