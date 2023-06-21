package com.example.paymentsv2.service

import com.example.paymentsv2.dtos.LoginResponse
import com.example.paymentsv2.dtos.UserDto
import com.example.paymentsv2.generated.client.CreateOrderGraphQLQuery
import com.example.paymentsv2.generated.client.CreateOrderProjectionRoot
import com.example.paymentsv2.generated.client.Rob_venuesGraphQLQuery
import com.example.paymentsv2.generated.client.Rob_venuesProjectionRoot
import com.example.paymentsv2.generated.types.OrderItemInput
import com.example.paymentsv2.models.Orders
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import com.netflix.graphql.dgs.internal.BaseDgsQueryExecutor.objectMapper
import graphql.ExecutionResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QueryService {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    fun loginQuery(user: UserDto?): String {

        val venues = venuesQuery()?.getData<LinkedHashMap<*,*>>()?.get(Rob_venuesGraphQLQuery().getOperationName()) as ArrayList<LinkedHashMap<Any,Any>>
        return objectMapper.writeValueAsString (
            LoginResponse(user!!, venues)
        )
    }

    fun venuesQuery(): ExecutionResult? {
        val gql = GraphQLQueryRequest(
            Rob_venuesGraphQLQuery.Builder()
                .build(),
            Rob_venuesProjectionRoot().id().name().description()
                .id().name().description()
                .menus().id().name().description()
                .menuItems().id().name().description().price().soldOut().quantity().isActive()
        ).serialize()

        return dgsQueryExecutor.execute(gql)
    }

    fun orderQuery(order: List<OrderItemInput>): Orders {
        val gql = GraphQLQueryRequest(
            CreateOrderGraphQLQuery.newRequest().order(
                order
            ).build(), CreateOrderProjectionRoot().id().total()
                .items().id()
                .menuItem().id().isActive().name().description().price()
        ).serialize()

        val executionResult = dgsQueryExecutor.execute(gql)

        val data = executionResult.getData<Map<String, Any>>()?.get(CreateOrderGraphQLQuery().getOperationName()) as? Map<String, Any>
        return Orders( data?.get("id") as? Long)
    }

//    fun myOrdersQuery(userId: Long): ArrayList<LinkedHashMap<Any, Any>> {
//        val gql = GraphQLQueryRequest(
//            MyordersGraphQLQuery.Builder().userId(userId)
//                .build(),
//            MyordersProjectionRoot().id().total()
//                .items().id().menuItem()
//        ).serialize()
//
//        val executionResult = dgsQueryExecutor.execute(gql)
//
//        val data = executionResult.getData<LinkedHashMap<*,*>>()?.get(MyordersGraphQLQuery().getOperationName()) as ArrayList<LinkedHashMap<Any,Any>>
//        //val data = executionResult.getData<Map<String, Any>>()?.get(CreateOrderGraphQLQuery().getOperationName()) as? Map<String, Any>
//        return data
//    }
}