package com.example.paymentsv2.robert.filters

import com.example.paymentsv2.robert.FilterFieldInterface
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

    fun createFilterField(filterArguments: Map<String, String>?, fieldType: Class<*>, name: String?): FilterFieldInterface? {
        if (filterArguments != null) {
            val queryOperator = QueryOperator.valueOf((filterArguments["queryOperator"] as String).toUpperCase())
            return when (fieldType) {
                IntFilterField::class.java -> IntFilterField(
                    value = filterArguments["value"]!!.toInt(),
                    operator = filterArguments["operator"],
                    name = name)
                FilterField::class.java -> FilterField(
                    value = filterArguments["value"],
                    operator = filterArguments["operator"])
                else -> null
            }
        }
        return null
    }
}