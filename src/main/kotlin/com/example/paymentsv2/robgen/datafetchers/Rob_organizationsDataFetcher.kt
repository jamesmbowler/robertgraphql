package com.example.paymentsv2.robgen.datafetchers

import com.example.paymentsv2.models.Organization
import com.example.paymentsv2.robgen.filters.RobOrganizationsFilter
import com.example.paymentsv2.robgen.repositories.Rob_organizationsRepository
import com.example.paymentsv2.utils.JoinChildren
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import graphql.schema.DataFetchingEnvironment
import kotlin.collections.List
import org.springframework.`data`.jpa.domain.Specification
import org.springframework.beans.factory.`annotation`.Autowired

@DgsComponent
public class Rob_organizationsDataFetcher {
  @Autowired
  public lateinit var repository: Rob_organizationsRepository

  @DgsQuery
  public fun rob_organizations(environment: DataFetchingEnvironment, @InputArgument
      filter: List<RobOrganizationsFilter>): List<Organization> {
    val spec:Specification<Organization>? =
        JoinChildren().fetchChildEntity(environment.selectionSet.immediateFields, filter)
    return repository.findAll(spec!!)
  }
}
