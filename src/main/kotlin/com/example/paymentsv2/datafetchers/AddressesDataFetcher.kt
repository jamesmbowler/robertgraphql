package com.example.paymentsv2.datafetchers

import com.example.paymentsv2.repositories.AddressRepository
import com.netflix.graphql.dgs.DgsComponent
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class AddressesDataFetcher {

    @Autowired
    lateinit var repository: AddressRepository

//    @DgsQuery
//    fun addresses(environment: DataFetchingEnvironment): List<Address> {
//
//        val spec: Specification<Address>? = JoinChildren().fetchChildEntity<Address>(
//            environment.selectionSet.immediateFields,
//            Address::class.java
//        )
//
//        return repository.findAll(spec!!)
//    }
}