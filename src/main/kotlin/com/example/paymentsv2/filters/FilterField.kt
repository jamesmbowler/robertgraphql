package com.example.paymentsv2.filters

import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
data class FilterField(
    var name: String? = null,
    val operator: String? = null,
    val value: String? = null,
    val queryOperator: QueryOperator = QueryOperator.AND,
    var type: Class<*> = String::class.java
) {
    fun <T> generateCriteria(builder: CriteriaBuilder, field: Path<T>): Predicate {
        when (this.operator) {
            "eq" -> return builder.equal(field, this.value)
            else -> null
        }  ?: throw Exception("could not find operator")
    }

    fun <T> generateCriteriaPredicate(builder: CriteriaBuilder, field: Path<T>): Predicate {

        if (type == Int::class.java) {
            val v = value!!.toInt()
            when (operator) {
                "lt" -> return builder.lessThan(field as Expression<Int>, v)
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

    fun <T> filter(): Specification<T>? {
        return Specification { root: Root<T>, query: CriteriaQuery<*>?, builder: CriteriaBuilder? ->
            this.generateCriteria<T>(
                builder!!, root.get (this.name)
            )
        }
    }
    fun addCondition(criteriaBuilder: CriteriaBuilder, get: Path<String>): Predicate {
        return generateCriteriaPredicate(
            criteriaBuilder, get
        )
    }
    fun <T> filtera(join: Join<Any, Any>): Specification<T>? {
        return Specification { root: Path<T>, query: CriteriaQuery<*>?, builder: CriteriaBuilder? ->
            this.generateCriteria<T>(
                builder!!, join.get(this.name)
            )
        }
    }
}
