//package com.example.paymentsv2.robgen.datafetchers
//
//import com.example.paymentsv2.models.Department
//import com.example.paymentsv2.robert.filters.Filter
//import com.example.paymentsv2.robert.filters.FilterGroup
//import com.example.paymentsv2.robert.utils.RobQueryBuilder
//import com.example.paymentsv2.robgen.repositories.Rob_departmentsRepository
//import com.netflix.graphql.dgs.DgsComponent
//import com.netflix.graphql.dgs.DgsQuery
//import com.netflix.graphql.dgs.InputArgument
//import graphql.schema.DataFetchingEnvironment
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.jpa.domain.Specification
//
//@DgsComponent
//public class Rob_departmentsDataFetcher {
//  @Autowired
//  public lateinit var repository: Rob_departmentsRepository
//
//  @DgsQuery
//  public fun rob_departments(environment: DataFetchingEnvironment, @InputArgument
//      filter: List<FilterGroup<out Filter>>?): List<Department> {
//    val spec:Specification<Department>? =
//        RobQueryBuilder().build(environment.selectionSet.immediateFields, filter)
//    return repository.findAll(spec!!)
//  }
//}
