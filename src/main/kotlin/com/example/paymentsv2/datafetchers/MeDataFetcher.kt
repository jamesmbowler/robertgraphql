package com.example.paymentsv2.datafetchers

import com.example.paymentsv2.models.User
import com.example.paymentsv2.repositories.UserRepository
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails

@DgsComponent
public class MeDataFetcher {
    @Autowired
    public lateinit var repository: UserRepository

    @DgsQuery
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public fun me(
        @AuthenticationPrincipal userDetails: UserDetails
    ): User? {
        return repository.findByEmail(userDetails.username)
    }
}
