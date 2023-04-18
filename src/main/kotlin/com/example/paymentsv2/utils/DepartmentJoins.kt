package com.example.paymentsv2.utils

import com.example.paymentsv2.models.Department
import com.example.paymentsv2.models.Employee
import com.example.paymentsv2.models.Organization
import jakarta.persistence.criteria.Fetch
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Root

class DepartmentJoins {
    companion object {
        fun getJoin(association: String, fetch: Fetch<Any, Any>): Fetch<out Any, out Any> {
            try {
                return when (association) {
                    "employees" -> fetch.fetch<Department, Employee>("employees", JoinType.LEFT)
                    "organizations" -> fetch.fetch<Department, Organization>("organizations", JoinType.LEFT)
                    else -> null
                } ?: throw Exception("No association $association")
            } catch (e: Exception ) {
                throw (e)
            }
        }

        fun <T> getRootJoin(
            association: String,
            root: Root<T>,
        ): Fetch<out Any, out Any> {
            try {
                return when (association) {
                    "employees" -> root.fetch<Department, Employee>("employees", JoinType.LEFT)
                    "organizations" -> root.fetch<Department, Organization>("organizations", JoinType.LEFT)
                    else -> null
                } ?: throw Exception("could not find association for departments")
            } catch (e: Exception ) {
                throw (e)
            }
        }
    }
}