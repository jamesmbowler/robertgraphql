package com.example.paymentsv2.utils

import com.example.paymentsv2.filters.EmployeeFilter
import com.example.paymentsv2.filters.Filter
import com.example.paymentsv2.models.Address
import com.example.paymentsv2.models.Department
import com.example.paymentsv2.models.Employee
import com.example.paymentsv2.models.Organization
import graphql.schema.*
import jakarta.persistence.criteria.Fetch
import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class JoinChildren {

    @Autowired
    var filterer: Filter = Filter()

    companion object {
        var joinFilters: MutableMap<String, Pair<Join<Any, Any>, MutableList<Filter>?>> = mutableMapOf()
    }

    fun <T>fetchChildEntity(
        fields: List<SelectedField>,
        parentEntityClass: Class<*>,
        //specs: Specification<T>?,
        rootFilters: List<Filter>?
    ): Specification<T>? {
        val filters: MutableSet<Pair<Join<Any,Any>, MutableList<Filter>>> = mutableSetOf()
        val predicates = mutableListOf<Predicate>()

        //val combinedSpec = specs.reduceOrNull { acc, spec -> acc?.and(spec) ?: spec }
        return Specification { root, criteriaQuery, criteriaBuilder ->
            joinRoot<T>(root, fields, parentEntityClass, filters)
            filters.forEach {
                val (join, filter) = it

                filter.forEach {
                    for (field in it.getFilters()) {
                        predicates.add(
                            field.addCondition(criteriaBuilder, join.get<String>(field.name))
                        )
                    }
                }
            }
            if (rootFilters != null) {
                for (f in rootFilters) {
                    for (field in f.getFilters()) {
                        predicates.add(
                            field.addCondition(criteriaBuilder, root.get(field.name))
                        )
                    }
                }
            }
            criteriaQuery.where(criteriaBuilder.and(*predicates.toTypedArray()))

//            if (specs != null) {
//                criteriaQuery.where(specs.toPredicate(root, criteriaQuery, criteriaBuilder))
//            }
            null
        }
    }

    private fun joinsHelper(
        parentEntityClass: Class<*>,
        f: Fetch<Any, Any>,
        fields: List<SelectedField>,
        filters: MutableSet<Pair<Join<Any, Any>, MutableList<Filter>>>
    ) {
        for (fi in fields) {
            if (fi.selectionSet.immediateFields.isNotEmpty()) {
                val fetch = fetchJoin(f, fi, parentEntityClass)
                joinsHelper(getType(fi.name), fetch as Fetch<Any, Any>, fi.selectionSet.immediateFields, filters)
            }
        }
    }

    private fun <T> joinRoot(
        root: Root<T>,
        fields: List<SelectedField>,
        parentEntityClass: Class<*>,
        filters: MutableSet<Pair<Join<Any, Any>, MutableList<Filter>>>
    ): Set<Pair<Join<Any, Any>, MutableList<Filter>>> {
        for (fi in fields) {
            if (fi.selectionSet.immediateFields.isNotEmpty()) {
                val fetch = rootFetch(root, fi, parentEntityClass) as Fetch<Any, Any>

                filters.addAll(filter(fi, fetch))

                joinsHelper(getType(fi.name), fetch, fi.selectionSet.immediateFields, filters)
            }
        }
        return filters
    }

    fun filter(field: SelectedField, fetch: Fetch<Any, Any>): MutableSet<Pair<Join<Any, Any>, MutableList<Filter>>> {
        val filterClass = getFilterClass(field)
        val filters: MutableSet<Pair<Join<Any, Any>, MutableList<Filter>>> = mutableSetOf()

        for (a in field.arguments) {
            filters.add(Pair(fetch as Join<Any,Any>, filterer.createFilter(a, filterClass)))
        }

        return filters
    }

    fun parseFilter(fieldDefinition: GraphQLFieldDefinition): GraphQLInputObjectType? {
        val filterArg = fieldDefinition.arguments.find { it.name == "filter" } ?: return null
        val filterType = filterArg.type as? GraphQLList ?: return null
        val wrappedType = getWrappedType(filterType)
        return wrappedType as? GraphQLInputObjectType ?: return null

    }
    fun getWrappedType(type: GraphQLType): GraphQLType {
        return if (type is GraphQLList) {
            getWrappedType(type.wrappedType)
        } else {
            type
        }
    }

    fun getFilterClass(field: SelectedField): Class<*>? {
        for (d in field.fieldDefinitions) {
            val filter = parseFilter(d)
            if (filter != null) {
                return getType(filter.name)
            }
        }
        return null
    }

    fun <T> rootFetch(
        root: Root<T>,
        field: SelectedField,
        parentEntityClass: Class<*>
    ): Fetch<out Any, out Any> {
        return getRootJoin(parentEntityClass, field.name, root)
    }

    fun fetchJoin(
        fetch: Fetch<Any, Any>,
        field: SelectedField,
        parentEntityClass: Class<*>
    ): Fetch<out Any, out Any>? {
        return getJoin(parentEntityClass, field.name, fetch)
    }

    fun getType(string: String): Class<out Any> {
        try {
            return when (string) {
                "employees", "employee" -> Employee::class.java
                "addresses", "address" -> Address::class.java
                "department", "departments" -> Department::class.java
                "organizations" -> Organization::class.java
                "EmployeeFilter" -> EmployeeFilter::class.java
                else -> null
            } ?: throw Exception("No class found for $string")
        } catch (e: Exception ) {
            throw (e)
        }
    }

    fun getJoin(
        parentEntityClass: Class<*>,
        joinField: String,
        fetch: Fetch<Any, Any>,
    ): Fetch<out Any, out Any> {
        return when(parentEntityClass) {
            Department::class.java -> DepartmentJoins.getJoin(joinField, fetch)
            Employee::class.java -> EmployeeJoins.getJoin(joinField, fetch)
            Address::class.java -> AddressJoins.getJoin(joinField, fetch)
            else -> {throw Exception("no entity class $parentEntityClass")}
        }
    }
    fun <T> getRootJoin(
        parentEntityClass: Class<*>,
        joinField: String,
        root: Root<T>
    ): Fetch<out Any, out Any> {
        return when(parentEntityClass) {
            Department::class.java -> DepartmentJoins.getRootJoin(joinField, root)
            Employee::class.java -> EmployeeJoins.getRootJoin(joinField, root)
            Address::class.java -> AddressJoins.getRootJoin(joinField, root)
            else -> {throw Exception("no entity class $parentEntityClass")}
        }
    }
}