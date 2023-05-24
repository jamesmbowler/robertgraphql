package com.example.paymentsv2.security

import com.example.paymentsv2.models.Role
import com.example.paymentsv2.repositories.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.function.Function
import java.util.stream.Collectors

@Service
class CustomUserDetailsService(userRepository: UserRepository) : UserDetailsService {
    private val userRepository: UserRepository

    init {
        this.userRepository = userRepository
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user: com.example.paymentsv2.models.User? = userRepository.findByEmail(email)
        return if (user != null) {
            User(
                user.email,
                user.password,
                mapRolesToAuthorities(user.roles)
            )
        } else {
            throw UsernameNotFoundException("Invalid username or password.")
        }
    }

    private fun mapRolesToAuthorities(roles: Collection<Role>): Collection<GrantedAuthority?> {
        return roles.stream()
            .map<SimpleGrantedAuthority?>(Function<Role, SimpleGrantedAuthority?> { role: Role ->
                SimpleGrantedAuthority(
                    role.name
                )
            })
            .collect(Collectors.toList<SimpleGrantedAuthority?>())
    }
}