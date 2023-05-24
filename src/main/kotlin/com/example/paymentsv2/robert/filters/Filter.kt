package com.example.paymentsv2.robert.filters

import com.example.paymentsv2.robert.FilterFieldInterface
import org.springframework.stereotype.Component
import kotlin.reflect.full.declaredMemberProperties

@Component
open class Filter {
    companion object {
        val fields = listOf<String>()
    }
    fun getFilters(): MutableList<FilterFieldInterface<*>> {
        return this.javaClass.kotlin.declaredMemberProperties
            .filter { property ->
                val value = property.get(this)
                (value is FilterFieldInterface<*>) && (value.value != null)
            }
            .map { property ->
                val value = property.get(this)
                if (value is FilterFieldInterface<*>) {
                    value.name = property.name
                }
                value
            }
            .filterIsInstance<FilterFieldInterface<*>>()
            .toMutableList()
    }

    fun createFilterField(filterArguments: Map<String, String>?, fieldType: Class<*>, name: String?): FilterFieldInterface<*>? {
        if (filterArguments != null) {
            val queryOperator = AndOrQueryOperator.valueOf((filterArguments["andOr"] as String).uppercase())
            return when (fieldType) {
                IntFilterField::class.java -> IntFilterField(
                    value = filterArguments["value"]!!.toInt(),
                    operator = filterArguments["operator"],
                    name = name,
                    andOr = queryOperator
                )
                FilterField::class.java -> FilterField(
                    value = filterArguments["value"],
                    operator = filterArguments["operator"],
                    andOr = queryOperator
                )
                else -> null
            }
        }
        return null
    }
}