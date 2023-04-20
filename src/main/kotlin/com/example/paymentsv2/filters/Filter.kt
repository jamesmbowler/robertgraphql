package com.example.paymentsv2.filters

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

//    fun <T> addSpecFilters(filters: List<Filter>?): Specification<T>? {
//        var resultSpec: Specification<T>? = null
//        if (filters !== null) {
//            for (f in filters) {
//                for (field in f.getFilters()) {
//                    resultSpec = if (field.queryOperator == QueryOperator.AND) {
//                        resultSpec?.and(field.filter()) ?: field.filter()
//                    } else {
//                        resultSpec?.or(field.filter()) ?: field.filter()
//                    }
//                }
//            }
//        }
//        return resultSpec
//    }

//    fun <T> addFilters(filters: List<Filter>?): Specification<T>? {
//        var resultSpec: Specification<T>? = null
//        if (filters !== null) {
//            for (f in filters) {
//                for (field in f.getFilters()) {
//                    resultSpec = if (field.queryOperator == QueryOperator.AND) {
//                        resultSpec?.and(field.filter()) ?: field.filter()
//                    } else {
//                        resultSpec?.or(field.filter()) ?: field.filter()
//                    }
//                }
//            }
//        }
//        return resultSpec
//    }

//    fun createFilter(a: MutableMap.MutableEntry<String, Any>, filterClass: Class<*>?): MutableList<Filter> {
//
//        val filterFields = mutableListOf<Filter>()
//
//        val filterMap = a.value as MutableList<Map<String, Any>>
//
//        filterMap.forEach { item ->
//            val idFilter = item["id"] as Map <String, Any>?
//            val firstNameFilter = item["firstName"] as Map<String, Any>?
//            val employeeFilter = EmployeeFilter(
//                id = createFilterField(idFilter),
//                firstName = createFilterField(firstNameFilter)
//            )
//            filterFields.add(employeeFilter)
//        }
//        return filterFields
//    }

    fun createFilter(a: MutableMap.MutableEntry<String, Any>, filterClass: Class<*>?): MutableList<Filter> {

        val filterFields = mutableListOf<Filter>()

        val filterMap = a.value as MutableList<Map<String, Any>>

        filterMap.forEach { item ->
            val filterConstructor = filterClass?.getDeclaredConstructor()
            // create an instance of the filter class using reflection
            val filterObj = filterConstructor?.newInstance() as Filter

            // iterate through the fields of the filter class using reflection
            filterClass?.declaredFields?.forEach { field ->
                // get the name of the field
                val fieldName = field.name
                // get the filter value from the filterMap based on the field name
                val filterValue = item[fieldName] as Map<String, Any>?
                // create a filter field using the createFilterField function
                val filterField = createFilterField(filterValue, field.type, fieldName)
                // set the value of the field on the filter object using reflection
                field.isAccessible = true
                field.set(filterObj, filterField)
            }

            filterFields.add(filterObj)
        }

        return filterFields
    }

    fun createFilterField(filterArguments: Map<String, Any>?, fieldType: Class<*>, name: String?): FilterField? {
        if (filterArguments != null) {
            val operator = filterArguments["operator"] as String
            val value = filterArguments["value"] as String
            val queryOperator = QueryOperator.valueOf((filterArguments["queryOperator"] as String).toUpperCase())
            return when (fieldType) {
//                Int::class.java -> IntFilterField(value as String?, operator as IntFilterOperators?)
//                String::class.java -> FilterField(value as String?, operator as StringFilterOperators?)
                IntFilterField::class.java -> IntFilterField(value = value, operator = operator, name = name)
                FilterField::class.java -> FilterField(value = value, operator = operator)
                else -> null
            }

           // return FilterField(operator = operator, value = value, queryOperator = queryOperator)
        }
        return null
    }
}