package com.example.paymentsv2.robert

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate

interface FilterFieldInterface {
    fun <T> generateCriteriaPredicate(builder: CriteriaBuilder, field: Path<T>): Predicate
    fun addCondition(criteriaBuilder: CriteriaBuilder, get: Path<String>): Predicate {
        return generateCriteriaPredicate(
            criteriaBuilder, get
        )
    }
}