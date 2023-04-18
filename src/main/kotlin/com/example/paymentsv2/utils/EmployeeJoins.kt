package com.example.paymentsv2.utils

import com.example.paymentsv2.models.Address
import com.example.paymentsv2.models.Employee
import jakarta.persistence.criteria.Fetch
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Root

class EmployeeJoins {
    companion object {
        fun getJoin(association: String, fetch: Fetch<Any, Any>, ): Fetch<out Any, out Any> {
            return when (association) {
                "addresses" -> fetch.fetch<Employee, Address>("addresses", JoinType.LEFT)
                else -> {throw Exception("No association $association")}
            }
        }
        fun <T>getRootJoin(association: String, root: Root<T>): Fetch<out Any, out Any> {
            return when (association) {
                "addresses" -> root.fetch<Employee, Address>("addresses", JoinType.LEFT)
                else -> {throw Exception("No association $association")}
            }
        }
    }
}