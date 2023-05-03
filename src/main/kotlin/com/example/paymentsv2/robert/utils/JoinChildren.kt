package com.example.paymentsv2.robert.utils

import com.example.paymentsv2.robert.filters.Filter
import com.example.paymentsv2.robgen.utils.FilterUtilClass
import graphql.schema.*
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class JoinChildren {

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
        for (field in fields) {
            if (field.selectionSet.immediateFields.isNotEmpty()) {
                val fetch: Fetch<Any, Any> = fetchJoin(f, field) as Fetch<Any, Any>
                filters.addAll(filter(field, fetch as Fetch<Any, Any>))
                joinsHelper(fetch, field.selectionSet.immediateFields, filters)
            }
        }
    }

    private fun <T> joinRoot(
        root: Root<T>,
        fields: List<SelectedField>,
        filters: MutableSet<Pair<Join<Any, Any>, MutableList<Filter>>>
    ): Set<Pair<Join<Any, Any>, MutableList<Filter>>> {
        for (field in fields) {
            if (field.selectionSet.immediateFields.isNotEmpty()) {
                val fetch = rootFetch(root, field) as Fetch<Any, Any>
                filters.addAll(filter(field, fetch))
                joinsHelper(fetch, field.selectionSet.immediateFields, filters)
            }
        }
        return filters
    }

    fun filter(field: SelectedField, fetch: Fetch<Any, Any>): MutableSet<Pair<Join<Any, Any>, MutableList<Filter>>> {
        field.arguments.entries.firstOrNull { it.key == "filter" }?.let {
            return mutableSetOf(
                Pair(fetch as Join<Any,Any>,
                    getFilterClass(field, it as MutableMap.MutableEntry<String, ArrayList<LinkedHashMap<String, LinkedHashMap<String, String>>>>
            )!!.toMutableList()))
        }

        return mutableSetOf()
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

    fun getFilterClass(
        field: SelectedField,
        arguments: MutableMap.MutableEntry<String, ArrayList<LinkedHashMap<String, LinkedHashMap<String, String>>>>): Set<Filter>
    {
        for (d in field.fieldDefinitions) {
            val filter = parseFilter(d)
            if (filter != null) {
                return FilterUtilClass().getType(filter.name, arguments!!)
            }
        }
        throw Exception("No filter found for ${field.name}")
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
}