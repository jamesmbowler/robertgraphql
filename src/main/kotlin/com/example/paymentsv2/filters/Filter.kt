package com.example.paymentsv2.filters

import jakarta.persistence.criteria.Join
import org.springframework.data.jpa.domain.Specification
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

    fun <T> addFilters(filters: List<Filter>?): Specification<T>? {
        var resultSpec: Specification<T>? = null
        if (filters !== null) {
            for (f in filters) {
                for (field in f.getFilters()) {
                    resultSpec = if (field.queryOperator == QueryOperator.AND) {
                        resultSpec?.and(field.filter()) ?: field.filter()
                    } else {
                        resultSpec?.or(field.filter()) ?: field.filter()
                    }
                }
            }
        }
        return resultSpec
    }

    fun <T> addJoinFilters(spec: Specification<T>?, filters: List<Filter>?, join: Join<Any,Any>): Specification<T>? {
        var resultSpec = spec
        if (filters !== null) {
            for (f in filters) {
                for (field in f.getFilters()) {
                    resultSpec = if (field.queryOperator == QueryOperator.AND) {
                        resultSpec?.and(field.filtera(join)) ?: field.filtera(join)
                    } else {
                        resultSpec?.or(field.filtera(join)) ?: field.filtera(join)
                    }
                }
            }
        }
        return resultSpec
    }

    fun createFilter(a: MutableMap.MutableEntry<String, Any>, filterClass: Class<*>?): MutableList<Filter> {

        val filterFields = mutableListOf<Filter>()

        val filterMap = a.value as MutableList<Map<String, Any>>

        filterMap.forEach { item ->
            val idFilter = item["id"] as Map <String, Any>?
            val firstNameFilter = item["firstName"] as Map<String, Any>?
            val employeeFilter = EmployeeFilter(
                id = createFilterField(idFilter),
                firstName = createFilterField(firstNameFilter)
            )
            filterFields.add(employeeFilter)
        }
        return filterFields
    }

    fun createFilterField(filterArguments: Map<String, Any>?): FilterField? {
        if (filterArguments != null) {
            val operator = filterArguments["operator"] as String
            val value = filterArguments["value"] as String
            val queryOperator = QueryOperator.valueOf((filterArguments["queryOperator"] as String).toUpperCase())
            return FilterField(operator = operator, value = value, queryOperator = queryOperator)
        }
        return null
    }
}