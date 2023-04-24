package com.example.paymentsv2.datafetchers

import com.example.paymentsv2.models.Address
import com.example.paymentsv2.repositories.AddressRepository
import com.example.paymentsv2.utils.JoinChildren
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification

@DgsComponent
class AddressesDataFetcher {

    @Autowired
    lateinit var repository: AddressRepository

    @DgsQuery
    fun addresses(environment: DataFetchingEnvironment): List<Address> {

        val spec: Specification<Address>? = JoinChildren().fetchChildEntity<Address>(
            environment.selectionSet.immediateFields,
            null
        )

        return repository.findAll(spec!!)
    }
}