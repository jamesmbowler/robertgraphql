package com.example.paymentsv2.utils

import com.example.paymentsv2.filters.EmployeeFilter
import com.example.paymentsv2.filters.Filter
import graphql.schema.*
import jakarta.persistence.criteria.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class JoinChildren {

    @Autowired
    var filterer: Filter = Filter()

    fun <T>fetchChildEntity(
        fields: List<SelectedField>,
        rootFilters: List<Filter>?
    ): Specification<T>? {
        val filters: MutableSet<Pair<Join<Any,Any>, MutableList<Filter>>> = mutableSetOf()
        val predicates = mutableListOf<Predicate>()

        return Specification { root, criteriaQuery, criteriaBuilder ->
            joinRoot<T>(root, fields, filters)
            filters.forEach {
                val (join, filter) = it
                filter.forEach {
                    for (field in it.getFilters()) {
                        predicates.add(
                            field.addCondition(criteriaBuilder, join.get(field.name))
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

            null
        }
    }

    private fun joinsHelper(
        f: Fetch<Any, Any>,
        fields: List<SelectedField>,
        filters: MutableSet<Pair<Join<Any, Any>, MutableList<Filter>>>
    ) {
        for (fi in fields) {
            if (fi.selectionSet.immediateFields.isNotEmpty()) {
                val fetch = fetchJoin(f, fi)
               // filters.addAll(filter(fi, fetch as Fetch<Any, Any>))
                joinsHelper(fetch as Fetch<Any, Any>, fi.selectionSet.immediateFields, filters)
            }
        }
    }

    private fun <T> joinRoot(
        root: Root<T>,
        fields: List<SelectedField>,
        filters: MutableSet<Pair<Join<Any, Any>, MutableList<Filter>>>
    ): Set<Pair<Join<Any, Any>, MutableList<Filter>>> {
        for (fi in fields) {
            if (fi.selectionSet.immediateFields.isNotEmpty()) {
                val fetch = rootFetch(root, fi) as Fetch<Any, Any>
                filters.addAll(filter(fi, fetch))
                joinsHelper(fetch, fi.selectionSet.immediateFields, filters)
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
        field: SelectedField
    ): Fetch<out Any, out Any> {
        return root.fetch(field.name, JoinType.LEFT)
    }

    fun fetchJoin(
        fetch: Fetch<Any, Any>,
        field: SelectedField
    ): Fetch<out Any, out Any>? {
        return fetch.fetch(field.name, JoinType.LEFT)
    }

    fun getType(string: String): Class<out Any> {
        try {
            return when (string) {
                "EmployeeFilter" -> EmployeeFilter::class.java
                else -> null
            } ?: throw Exception("No class found for $string")
        } catch (e: Exception ) {
            throw (e)
        }
    }
}