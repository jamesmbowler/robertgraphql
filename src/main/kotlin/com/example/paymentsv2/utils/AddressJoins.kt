package com.example.paymentsv2.utils

import com.example.paymentsv2.models.Address
import com.example.paymentsv2.models.Employee
import jakarta.persistence.criteria.Fetch
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Root

class AddressJoins {
    companion object {
        fun getJoin(association: String, fetch: Fetch<Any, Any>): Fetch<out Any, out Any> {
            try {
                return when (association) {
                    "employees" -> fetch.fetch<Address, Employee>("employees", JoinType.LEFT)
                    else -> null
                } ?: throw Exception("No association $association")
            } catch (e: Exception ) {
                throw (e)
            }
        }

        fun <T> getRootJoin(association: String, root: Root<T>): Fetch<out Any, out Any> {
            return when (association) {
                "employees" -> root.fetch<Address, Employee>("employees", JoinType.LEFT)
                else -> {
                    throw Exception("No association $association")
                }
            }
        }
    }
}