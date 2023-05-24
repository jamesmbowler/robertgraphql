package com.example.paymentsv2.robert.filters

import com.example.paymentsv2.robert.FilterFieldInterface
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import org.springframework.stereotype.Component

@Component
data class FilterField(
    override var name: String? = null,
    override val value: String? = null,
    var operator: String? = null,
    override var andOr: AndOrQueryOperator? = AndOrQueryOperator.AND
): FilterFieldInterface<String?> {
    override fun <T> generateCriteriaPredicate(
        builder: CriteriaBuilder,
        field: Path<T>,
        andOr: AndOrQueryOperator?
    ): Predicate {
        val predicate = when (operator) {
            "eq" -> builder.equal(field, value)
            "endsWith" -> builder.like(field as Expression<String>, "%$value")
            "startsWith" -> builder.like(field as Expression<String>, "$value%")
            "contains" -> builder.like(field as Expression<String>, "%$value%")
            else -> {
                throw Exception("No criteria")}
        }
        return getQueryOperator(builder, predicate, andOr)
    }
}
