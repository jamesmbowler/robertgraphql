package com.example.paymentsv2.filters

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
//    fun <T> generateCriteria(builder: CriteriaBuilder, field: Path<T>): Predicate {
//
//        when (this.operator) {
//            "eq" -> return builder.equal(field, this.value)
//            else -> null
//        }  ?: throw Exception("could not find operator")
//    }

    fun <T> generateCriteriaPredicate(builder: CriteriaBuilder, field: Path<T>): Predicate {

        if (type == Int::class.java) {
            val v = value!!.toInt()
            when (operator) {
//                IntFilterOperators.LT -> return builder.lessThan(field as Expression<Int>, v)
//                IntFilterOperators.LE -> return builder.lessThanOrEqualTo(field as Expression<Int>, v)
//                IntFilterOperators.GT -> return builder.greaterThan(field as Expression<Int>, v)
//                IntFilterOperators.GE -> return builder.greaterThanOrEqualTo(field as Expression<Int>, v)
//                IntFilterOperators.EQ -> return builder.equal(field as Expression<Int>, v)
                "lt" -> return builder.lessThan(field as Expression<Int>, v)
                "le" -> return builder.lessThanOrEqualTo(field as Expression<Int>, v)
                "gt" -> return builder.greaterThan(field as Expression<Int>, v)
                "ge" -> return builder.greaterThanOrEqualTo(field as Expression<Int>, v)
                "eq" -> return builder.equal(field as Expression<Int>, v)
            }
        }

        when (operator) {
//            StringFilterOperators.EQ -> return builder.equal(field, value)
//            StringFilterOperators.ENDS_WITH -> return builder.like(field as Expression<String>, "%$value")
//            StringFilterOperators.STARTS_WITH -> return builder.like(field as Expression<String>, "$value%")
//            StringFilterOperators.CONTAINS -> return builder.like(field as Expression<String>, "%$value%")
            "eq" -> return builder.equal(field, value)
            "endsWith" -> return builder.like(field as Expression<String>, "%$value")
            "startsWith" -> return builder.like(field as Expression<String>, "$value%")
            "contains" -> return builder.like(field as Expression<String>, "%$value%")
        }
        throw Exception("No criteria")
    }

//    fun <T> filter(): Specification<T>? {
//        return Specification { root: Root<T>, query: CriteriaQuery<*>?, builder: CriteriaBuilder? ->
//            this.generateCriteria<T>(
//                builder!!, root.get (this.name)
//            )
//        }
//    }
    fun addCondition(criteriaBuilder: CriteriaBuilder, get: Path<String>): Predicate {
        return generateCriteriaPredicate(
            criteriaBuilder, get
        )
    }
//    fun <T> filtera(join: Join<Any, Any>): Specification<T>? {
//        return Specification { root: Path<T>, query: CriteriaQuery<*>?, builder: CriteriaBuilder? ->
//            this.generateCriteria<T>(
//                builder!!, join.get(this.name)
//            )
//        }
//    }
}
