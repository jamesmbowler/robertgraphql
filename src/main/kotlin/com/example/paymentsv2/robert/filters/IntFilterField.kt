package com.example.paymentsv2.robert.filters

import com.example.paymentsv2.robert.FilterFieldInterface
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import org.springframework.stereotype.Component

@Component
data class IntFilterField(
    val value: Int? = null,
    var name: String? = null,
    val operator: String? = null,
    //override val operator: IntFilterOperators? = null,
    val queryOperator: QueryOperator = QueryOperator.AND
): FilterFieldInterface {
    override fun <T> generateCriteriaPredicate(builder: CriteriaBuilder, field: Path<T>): Predicate {
        val v = value!!.toInt()
        when (operator) {
            "lt" -> return builder.lessThan(field as Expression<Int>, v)
            "le" -> return builder.lessThanOrEqualTo(field as Expression<Int>, v)
            "gt" -> return builder.greaterThan(field as Expression<Int>, v)
            "ge" -> return builder.greaterThanOrEqualTo(field as Expression<Int>, v)
            "eq" -> return builder.equal(field as Expression<Int>, v)
        }

        throw Exception("No criteria")
    }
}