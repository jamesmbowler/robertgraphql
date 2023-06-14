package com.example.paymentsv2.robert.filters

import com.example.paymentsv2.robert.FilterFieldInterface
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import org.springframework.stereotype.Component

@Component
data class EnumFilterField<T>(
    override val value: T? = null,
    override var name: String? = null,
    val operator: String? = null,
    //override val operator: IntFilterOperators? = null,
    override var andOr: AndOrQueryOperator? = AndOrQueryOperator.AND
): FilterFieldInterface<T?> {

    override fun <T> generateCriteriaPredicate(
        builder: CriteriaBuilder,
        field: Path<T>,
        andOr: AndOrQueryOperator?
    ): Predicate {
        val v = value
        val predicate = when (operator) {
            "eq" -> builder.equal(field as Expression<T>, v)
            else -> {
                throw Exception("No criteria")}
        }

        return getQueryOperator(builder, predicate, andOr)
    }
}