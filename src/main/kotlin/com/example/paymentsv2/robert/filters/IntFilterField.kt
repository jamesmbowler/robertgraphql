package com.example.paymentsv2.robert.filters

import com.example.paymentsv2.robert.FilterFieldInterface
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import org.springframework.stereotype.Component

@Component
data class IntFilterField(
    override val value: Int? = null,
    override var name: String? = null,
    val operator: String? = null,
    //override val operator: IntFilterOperators? = null,
    override var andOr: AndOrQueryOperator? = AndOrQueryOperator.AND
): FilterFieldInterface<Int?> {

    override fun <T> generateCriteriaPredicate(
        builder: CriteriaBuilder,
        field: Path<T>,
        andOr: AndOrQueryOperator?
    ): Predicate {
        val v = value!!.toInt()
        val predicate = when (operator) {
            "lt" -> builder.lessThan(field as Expression<Int>, v)
            "le" -> builder.lessThanOrEqualTo(field as Expression<Int>, v)
            "gt" -> builder.greaterThan(field as Expression<Int>, v)
            "ge" -> builder.greaterThanOrEqualTo(field as Expression<Int>, v)
            "eq" -> builder.equal(field as Expression<Int>, v)
            else -> {
                throw Exception("No criteria")}
        }

        return getQueryOperator(builder, predicate, andOr)
    }
}