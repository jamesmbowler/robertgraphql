package com.example.paymentsv2.controllers

import com.example.paymentsv2.generated.client.Rob_departmentsGraphQLQuery
import com.example.paymentsv2.generated.client.Rob_departmentsProjectionRoot
import com.example.paymentsv2.generated.client.Rob_venuesGraphQLQuery
import com.example.paymentsv2.service.QueryService
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController(
    @Autowired val queryService: QueryService
) {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

//    @GetMapping("test")
    fun test(): String {
        val gql = GraphQLQueryRequest(
             Rob_departmentsGraphQLQuery.Builder()
                .build(),
            Rob_departmentsProjectionRoot().id().description().employees().firstName().addresses().id().street()

        ).serialize()

        val executionResult = dgsQueryExecutor.execute(gql)

        return linkedHashMapToString(executionResult.getData<LinkedHashMap<*,*>>())
    }

    @GetMapping("venues")
    fun venues(): ArrayList<LinkedHashMap<Any, Any>> {
        return queryService.venuesQuery()?.getData<LinkedHashMap<*,*>>()?.get(Rob_venuesGraphQLQuery().getOperationName()) as ArrayList<LinkedHashMap<Any,Any>>
    }

    fun linkedHashMapToString(map: LinkedHashMap<*, *>): String {
        val sb = StringBuilder("{")
        for ((key, value) in map) {
            sb.append("\"$key\": ")
            if (value is LinkedHashMap<*, *>) {
                sb.append(linkedHashMapToString(value))
            } else {
                sb.append("\"$value\"")
            }
            sb.append(", ")
        }
        if (sb.length > 1) {
            sb.setLength(sb.length - 2)
        }
        sb.append("}")
        return sb.toString()
    }
}