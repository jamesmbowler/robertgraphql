package com.example.paymentsv2.datafetchers

import com.example.paymentsv2.repositories.EmployeeRepository
import com.netflix.graphql.dgs.DgsComponent
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class EmployeesDataFetcher {

    @Autowired
    lateinit var repository: EmployeeRepository

//    @DgsQuery
//    fun employees(environment: DataFetchingEnvironment, @InputArgument filter: EmployeeFilter? = null): List<Employee> {
//
//        val spec: Specification<Employee>? = JoinChildren().fetchChildEntity<Employee>(
//            environment.selectionSet.immediateFields,
//            Employee::class.java
//        )
//
//        return repository.findAll(spec!!)
//    }
}