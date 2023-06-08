package com.example.paymentsv2.robgen.datafetchers

import com.example.paymentsv2.models.Orders
import com.example.paymentsv2.repositories.UserRepository
import com.example.paymentsv2.robert.filters.Filter
import com.example.paymentsv2.robert.filters.FilterGroup
import com.example.paymentsv2.robert.filters.IntFilterField
import com.example.paymentsv2.robert.utils.RobQueryBuilder
import com.example.paymentsv2.robgen.filters.OrdersFilter
import com.example.paymentsv2.robgen.repositories.Rob_ordersRepository
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails

@DgsComponent
public class Rob_ordersDataFetcher {
  @Autowired
  public lateinit var repository: Rob_ordersRepository

  @Autowired
  public lateinit var userRepository: UserRepository

  @DgsQuery
  public fun rob_orders(
      environment: DataFetchingEnvironment,
      @InputArgument filter: List<FilterGroup<out Filter>>?,
      @AuthenticationPrincipal userDetails: UserDetails
  ): List<Orders> {
    val spec:Specification<Orders>? =
        RobQueryBuilder().build(environment.selectionSet.immediateFields, filter)
    return repository.findAll(spec!!)
  }
    @DgsQuery
    public fun myorders(
        environment: DataFetchingEnvironment,
        @InputArgument filter: List<FilterGroup<out Filter>> = listOf(),
        @AuthenticationPrincipal userDetails: UserDetails
    ): List<Orders> {
        val mutableFilter = filter.toMutableList()

        mutableFilter.add(FilterGroup(filter = listOf(
            OrdersFilter(userId = IntFilterField(
                value = userRepository.findByEmail(userDetails.username)?.id?.toInt(),
                operator = "eq"
            )
        ))))

        val spec:Specification<Orders>? =
            RobQueryBuilder().build(environment.selectionSet.immediateFields, mutableFilter.toList())
        return repository.findAll(spec!!)
    }
}
