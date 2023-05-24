package com.example.paymentsv2.robgen.datafetchers

import com.example.paymentsv2.models.Venues
import com.example.paymentsv2.robert.filters.Filter
import com.example.paymentsv2.robert.filters.FilterGroup
import com.example.paymentsv2.robert.utils.RobQueryBuilder
import com.example.paymentsv2.robgen.repositories.Rob_venuesRepository
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import graphql.schema.DataFetchingEnvironment
import kotlin.collections.List
import org.springframework.`data`.jpa.domain.Specification
import org.springframework.beans.factory.`annotation`.Autowired

@DgsComponent
public class Rob_venuesDataFetcher {
  @Autowired
  public lateinit var repository: Rob_venuesRepository

  @DgsQuery
  public fun rob_venues(environment: DataFetchingEnvironment, @InputArgument
      filter: List<FilterGroup<out Filter>>?): List<Venues> {
    val spec:Specification<Venues>? =
        RobQueryBuilder().build(environment.selectionSet.immediateFields, filter)
    return repository.findAll(spec!!)
  }
}
