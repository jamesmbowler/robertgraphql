package com.example.paymentsv2.robgen.datafetchers

import com.example.paymentsv2.models.Orders
import com.example.paymentsv2.robert.filters.Filter
import com.example.paymentsv2.robert.filters.FilterGroup
import com.example.paymentsv2.robert.utils.RobQueryBuilder
import com.example.paymentsv2.robgen.repositories.Rob_ordersRepository
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import graphql.schema.DataFetchingEnvironment
import kotlin.collections.List
import org.springframework.`data`.jpa.domain.Specification
import org.springframework.beans.factory.`annotation`.Autowired

@DgsComponent
public class Rob_ordersDataFetcher {
  @Autowired
  public lateinit var repository: Rob_ordersRepository

  @DgsQuery
  public fun rob_orders(environment: DataFetchingEnvironment, @InputArgument
      filter: List<FilterGroup<out Filter>>?): List<Orders> {
    val spec:Specification<Orders>? =
        RobQueryBuilder().build(environment.selectionSet.immediateFields, filter)
    return repository.findAll(spec!!)
  }
}
