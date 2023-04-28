package com.example.paymentsv2.robert.filters

import org.springframework.stereotype.Component

@Component
class IntFilterField(
    override val value: String? = null,
    override var name: String? = null,
    override val operator: String? = null,
    //override val operator: IntFilterOperators? = null,
    override val queryOperator: QueryOperator = QueryOperator.AND
): FilterField(
    name = name,
    type = Int::class.java,
    value = value,
    operator = operator,
    queryOperator = queryOperator
) {

}
