package com.example.paymentsv2.config

import com.example.paymentsv2.repositories.UserRepository
import com.example.paymentsv2.service.QueryService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component


@Configuration
@EnableWebSecurity
class SpringSecurity @Autowired constructor(
    val queryService: QueryService,
    val userRepository: UserRepository
)  {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private val userDetailsService: UserDetailsService? = null

    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManager(http: HttpSecurity): AuthenticationManager? {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .build()
    }

    @Bean
    @Order(1)
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/register/**").permitAll()
                .requestMatchers("/index").permitAll()
                .requestMatchers("/login/**").permitAll()
                .requestMatchers("/venues").permitAll()
                authorize.requestMatchers("/venues").hasAnyRole("ADMIN", "USER")
                authorize.requestMatchers("/graphql").hasAnyRole("ADMIN", "USER")
                authorize.requestMatchers("/graphiql/**").hasRole("ADMIN")
                authorize.requestMatchers("/order/**").hasAnyRole("ADMIN", "USER")
                authorize.requestMatchers("/users").hasRole("ADMIN")
                authorize.requestMatchers("/adduser/**").hasRole("ADMIN")
                authorize.requestMatchers("/newuser/**").hasRole("ADMIN")
            }
            //.addFilterBefore(JsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .formLogin { form ->
                form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/users")
                    .successHandler(CustomAuthenticationSuccessHandler(
                        objectMapper,
                        userDetailsService!!,
                        queryService,
                        userRepository
                    ))
                    .failureHandler(CustomAuthenticationFailureHandler())
                    .permitAll()
            }
            .rememberMe { rememberMe ->
                rememberMe
                    .key("uniqueAndSecret")
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(15770000)
                    //.rememberMeCookieName("yourRememberMeCookie")
                    .rememberMeServices(tokenBasedRememberMeServices())
            }
            //.addFilterAt(jsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .logout { logout ->
                logout
                    .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                    .permitAll()
            }
        return http.build()
    }

    fun tokenBasedRememberMeServices(): TokenBasedRememberMeServices? {
        val rememberMeServices = TokenBasedRememberMeServices("uniqueAndSecret", userDetailsService)
        rememberMeServices.setAlwaysRemember(true)
        return rememberMeServices
    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
    }

    companion object {
        @Bean
        fun passwordEncoder(): PasswordEncoder {
            return BCryptPasswordEncoder()
        }
    }
}

@Component
class CustomAuthenticationSuccessHandler(
    private val objectMapper: ObjectMapper,
    private val userDetailsService: UserDetailsService,
    val queryService: QueryService,
    val userRepository: UserRepository,
) : AuthenticationSuccessHandler {

    var logger: org.slf4j.Logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler::class.java)

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?
    ) {
        if (request.getHeader("X-App-Header").isNullOrEmpty()) {
            return response.sendRedirect("/users")
        }
        val fcmToken = request.getParameter("fcmToken")

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_OK

        val user = userRepository.findByEmail(authentication?.name)

        if (user != null) {
            user.fcmToken = fcmToken
            userRepository.save(user)
        }
//        val session = request.session
//        val sessionToken = session.id

        // Add the session token to the response

        // Add the session token to the response
       // response.addHeader("Cookie", sessionToken)

        //val jsonResponse = "{\"message\": \"Login Succeeded\"}"
        //logger?.info("Login Succeeded")
        response.writer?.write(
            queryService.loginQuery(
                user?.toDto()
            ))
    }
}

@Component
class CustomAuthenticationFailureHandler : AuthenticationFailureHandler {

    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write("{\"message\": \"Login failed\"}")
        response.writer.flush()
    }
}