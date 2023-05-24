package com.example.paymentsv2.robert

import com.example.paymentsv2.robert.filters.AndOrQueryOperator
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate

interface FilterFieldInterface<T> {

    val value: T

    var name: String?

    var andOr: AndOrQueryOperator?

    fun <T> generateCriteriaPredicate(builder: CriteriaBuilder, field: Path<T>, andOr: AndOrQueryOperator?): Predicate
    fun addCondition(criteriaBuilder: CriteriaBuilder, get: Path<String>, andOr: AndOrQueryOperator?): Predicate {
        return generateCriteriaPredicate(
            criteriaBuilder, get, andOr
        )
    }
    fun getQueryOperator(criteriaBuilder: CriteriaBuilder, predicate: Predicate, andOr: AndOrQueryOperator?): Predicate {
        return when (andOr) {
            AndOrQueryOperator.AND -> criteriaBuilder.and(predicate)
            AndOrQueryOperator.OR -> criteriaBuilder.or(predicate)
            else -> criteriaBuilder.and(predicate)
        }
    }
}