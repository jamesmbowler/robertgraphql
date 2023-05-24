//package com.example.paymentsv2.robgen.datafetchers
//
//import com.example.paymentsv2.models.Employee
//import com.example.paymentsv2.robert.utils.RobQueryBuilder
//import com.example.paymentsv2.robgen.filters.RobEmployeeFilter
//import com.example.paymentsv2.robgen.repositories.Rob_employeesRepository
//import com.netflix.graphql.dgs.DgsComponent
//import com.netflix.graphql.dgs.DgsQuery
//import com.netflix.graphql.dgs.InputArgument
//import graphql.schema.DataFetchingEnvironment
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.jpa.domain.Specification
//
//@DgsComponent
//public class Rob_employeesDataFetcher {
//  @Autowired
//  public lateinit var repository: Rob_employeesRepository
//
//  @DgsQuery
//  public fun rob_employees(environment: DataFetchingEnvironment, @InputArgument
//      filter: List<List<RobEmployeeFilter>>): List<Employee> {
//    val spec:Specification<Employee>? =
//        RobQueryBuilder().build(environment.selectionSet.immediateFields, filter)
//    return repository.findAll(spec!!)
//  }
//}
