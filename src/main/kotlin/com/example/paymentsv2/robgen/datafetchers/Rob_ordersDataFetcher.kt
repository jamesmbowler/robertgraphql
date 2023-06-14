package com.example.paymentsv2.robgen.datafetchers

import com.example.paymentsv2.models.Orders
import com.example.paymentsv2.repositories.UserRepository
import com.example.paymentsv2.robert.filters.*
import com.example.paymentsv2.robert.utils.OrderStatus
import com.example.paymentsv2.robert.utils.RobQueryBuilder
import com.example.paymentsv2.robgen.filters.OrdersFilter
import com.example.paymentsv2.robgen.repositories.Rob_ordersRepository
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails

@DgsComponent
public class Rob_ordersDataFetcher {
  @Autowired
  public lateinit var repository: Rob_ordersRepository

  @Autowired
  public lateinit var userRepository: UserRepository

  @DgsQuery
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public fun admin_allorders(
      environment: DataFetchingEnvironment,
      @InputArgument status: OrderStatus? = null,
      //@InputArgument filter: List<FilterGroup<out Filter>>? = listOf()
  ): List<Orders> {

      val mutableFilter = mutableListOf<FilterGroup<out Filter>>()
      val order = mapOf(
          "id" to "DESC"
      )
      if (status != null){
          mutableFilter.add(FilterGroup(filter = listOf(
              OrdersFilter(
                  status = EnumFilterField(
                      value = status,
                      operator = "eq"
                  )
              )
          )))
      }

      //mutableFilter.add(FilterGroup(filter = orderFilters))
    val spec:Specification<Orders>? =
        RobQueryBuilder().build(environment.selectionSet.immediateFields, mutableFilter, order)
    return repository.findAll(spec!!)
  }
    @DgsQuery
    public fun myorders(
        environment: DataFetchingEnvironment,
        @InputArgument filter: List<FilterGroup<out Filter>> = listOf(),
        @InputArgument id: Int? = null,
        @AuthenticationPrincipal userDetails: UserDetails
    ): List<Orders> {
        val mutableFilter = filter.toMutableList()
        val orderFilters = listOf(
            OrdersFilter(userId = IntFilterField(
                value = userRepository.findByEmail(userDetails.username)?.id?.toInt(),
                operator = "eq"
        )))

        if (id != null) {
            orderFilters.plus(OrdersFilter(id = IntFilterField(
                value = id,
                operator = "eq"
            )))
        }

        mutableFilter.add(FilterGroup(filter = orderFilters))

        val order = mapOf(
            "id" to "DESC"
        )
        val spec:Specification<Orders>? =
            RobQueryBuilder().build(
                environment.selectionSet.immediateFields,
                mutableFilter.toList(),
                order
            )

        return repository.findAll(spec!!)
    }
}
