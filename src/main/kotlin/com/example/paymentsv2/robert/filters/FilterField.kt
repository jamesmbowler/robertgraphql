package com.example.paymentsv2.robert.filters

import com.example.paymentsv2.robert.FilterFieldInterface
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
): FilterFieldInterface {
    override fun <T> generateCriteriaPredicate(builder: CriteriaBuilder, field: Path<T>): Predicate {
        when (operator) {
            "eq" -> return builder.equal(field, value)
            "endsWith" -> return builder.like(field as Expression<String>, "%$value")
            "startsWith" -> return builder.like(field as Expression<String>, "$value%")
            "contains" -> return builder.like(field as Expression<String>, "%$value%")
        }
        throw Exception("No criteria")
    }
}
