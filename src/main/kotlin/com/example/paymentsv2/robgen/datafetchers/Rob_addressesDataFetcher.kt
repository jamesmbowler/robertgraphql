package com.example.paymentsv2.robgen.datafetchers

import com.example.paymentsv2.models.Address
import com.example.paymentsv2.robgen.filters.RobAddressFilter
import com.example.paymentsv2.robgen.repositories.Rob_addressesRepository
import com.example.paymentsv2.utils.JoinChildren
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification

@DgsComponent
public class Rob_addressesDataFetcher {
  @Autowired
  public lateinit var repository: Rob_addressesRepository

  @DgsQuery
  public fun rob_addresses(environment: DataFetchingEnvironment, @InputArgument
      filter: List<RobAddressFilter>): List<Address> {
    val spec:Specification<Address>? =
        JoinChildren().fetchChildEntity(environment.selectionSet.immediateFields, filter)
    return repository.findAll(spec!!)
  }
}
