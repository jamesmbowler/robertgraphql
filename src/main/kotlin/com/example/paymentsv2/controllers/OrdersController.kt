package com.example.paymentsv2.controllers

import com.example.paymentsv2.repositories.UserRepository
import com.example.paymentsv2.service.QueryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

@RestController
class OrdersController(
    @Autowired val queryService: QueryService,
    var userRepository: UserRepository
) {

//    @GetMapping("myorders")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//    fun myorders(@AuthenticationPrincipal userDetails: UserDetails): ArrayList<LinkedHashMap<Any, Any>> {
//        return queryService.myOrdersQuery(userRepository.findByEmail(userDetails.username)?.id!!)
//    }
}