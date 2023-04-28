package com.example.paymentsv2.robert.filters

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import org.springframework.stereotype.Component

@Component
data class FilterField(
    var name: String? = null,
    val operator: String? = null,
    val value: String? = null,
    val queryOperator: QueryOperator = QueryOperator.AND,
    var type: Class<*> = String::class.java
) {
    fun <T> generateCriteriaPredicate(builder: CriteriaBuilder, field: Path<T>): Predicate {

        if (type == Int::class.java) {
            val v = value!!.toInt()
            when (operator) {
                "lt" -> return builder.lessThan(field as Expression<Int>, v)
                "le" -> return builder.lessThanOrEqualTo(field as Expression<Int>, v)
                "gt" -> return builder.greaterThan(field as Expression<Int>, v)
                "ge" -> return builder.greaterThanOrEqualTo(field as Expression<Int>, v)
                "eq" -> return builder.equal(field as Expression<Int>, v)
            }
        }

        when (operator) {
            "eq" -> return builder.equal(field, value)
            "endsWith" -> return builder.like(field as Expression<String>, "%$value")
            "startsWith" -> return builder.like(field as Expression<String>, "$value%")
            "contains" -> return builder.like(field as Expression<String>, "%$value%")
        }
        throw Exception("No criteria")
    }

    fun addCondition(criteriaBuilder: CriteriaBuilder, get: Path<String>): Predicate {
        return generateCriteriaPredicate(
            criteriaBuilder, get
        )
    }
}
