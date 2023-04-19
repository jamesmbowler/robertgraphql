package com.example.paymentsv2.datafetchers

import com.example.paymentsv2.filters.DepartmentFilter
import com.example.paymentsv2.filters.Filter
import com.example.paymentsv2.filters.FilterField
import com.example.paymentsv2.models.Department
import com.example.paymentsv2.repositories.DepartmentRepository
import com.example.paymentsv2.utils.JoinChildren
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import graphql.schema.DataFetchingEnvironment
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.Root
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification

@DgsComponent
class DepartmentsDataFetcher {

    @Autowired
    lateinit var repository: DepartmentRepository

    @Autowired
    lateinit var filterer: Filter

    @DgsQuery
    fun departments(
        environment: DataFetchingEnvironment,
        @InputArgument filter: List<DepartmentFilter>? = null
    ): List<Department> {

        val spec:Specification<Department>? = JoinChildren().fetchChildEntity(
            environment.selectionSet.immediateFields,
            Department::class.java,
            filterer.addFilters(filter)
        )

        //return repository.findAll()
        return repository.findAll(spec!!)
    }

    fun filter(filterField: FilterField, name: String, join: Join<Any, Any>): Specification<Department?>? {
        return Specification<Department?> { root: Root<Department?>, query: CriteriaQuery<*>?, builder: CriteriaBuilder ->
            filterField.generateCriteria(
                builder,
                join.get<String>(name)
            )
        }
    }

//    @DgsData(parentType = "Department", field = "employees")
//    fun employees(
//        environment: DataFetchingEnvironment,
//        @InputArgument filter: List<EmployeeFilter>? = null
//    ): List<Employee>? {
//
//        var test = filter
//
//        return null
//    }
}