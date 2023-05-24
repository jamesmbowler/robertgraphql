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
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component


@Configuration
@EnableWebSecurity
class SpringSecurity @Autowired constructor(
    val queryService: QueryService,
    val userRepository: UserRepository
) {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private val userDetailsService: UserDetailsService? = null

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/register/**").permitAll()
                    .requestMatchers("/graphiql/**").permitAll()
                    .requestMatchers("/graphql/**").permitAll()
                    .requestMatchers("/test/**").permitAll()
                    .requestMatchers("/graphql").permitAll()
                    .requestMatchers("/index").permitAll()
                    .requestMatchers("/venues").permitAll()
                    .requestMatchers("/order").permitAll()
                    .requestMatchers("/users").hasRole("ADMIN")
            }
            //.addFilterBefore(JsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .formLogin { form ->
                form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/users")
                    .permitAll()
            }
            .formLogin { form ->
                form
                    //.loginPage("/login_m")
                    .loginProcessingUrl("/login_m")
                    .defaultSuccessUrl("/")
                    .successHandler(CustomAuthenticationSuccessHandler(
                        objectMapper,
                        userDetailsService!!,
                        queryService,
                        userRepository
                    ))
                    .failureHandler(CustomAuthenticationFailureHandler())
                    .permitAll()
            }
            //.addFilterAt(jsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .logout { logout ->
                logout
                    .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                    .permitAll()
            }
        return http.build()
    }

//    fun jsonAuthenticationFilter(): AuthenticationFilter {
//        val filter = UsernamePasswordAuthenticationFilter()
//        //filter.setAuthenticationManager(authenticationManagerBean(AuthenticationManagerBuilder())
//        filter.setAuthenticationSuccessHandler(CustomAuthenticationSuccessHandler(objectMapper, userDetailsService!!))
//        filter.setFilterProcessesUrl("/login_m")
//        filter.setPostOnly(true)
//        filter.setUsernameParameter("username")
//        filter.setPasswordParameter("password")
//        filter.setAuthenticationConverter(JsonAuthenticationConverter(objectMapper))
//        return filter
//    }

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

    var logger: org.slf4j.Logger? = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler::class.java)

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        response?.status = HttpServletResponse.SC_OK

        //val jsonResponse = "{\"message\": \"Login Succeeded\"}"
        logger?.info("Login Succeeded")
        response?.writer?.write(
            queryService.loginQuery(
                userRepository.findByEmail(authentication?.name)?.toDto()
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

//class JsonAuthenticationConverter(private val objectMapper: ObjectMapper) : AuthenticationConverter {
//    override fun convert(request: HttpServletRequest): Authentication? {
//        try {
//            val jsonNode = objectMapper.readTree(request.reader)
//            val username = jsonNode.get("username").textValue()
//            val password = jsonNode.get("password").textValue()
//            return UsernamePasswordAuthenticationToken(username, password)
//        } catch (e: Exception) {
//            return null
//        }
//    }
//}

//class JsonUsernamePasswordAuthenticationFilter() :
//    UsernamePasswordAuthenticationFilter() {
//
//    @Autowired
//    lateinit var authentication: AuthenticationManager
//
//    override fun getAuthenticationManager(): AuthenticationManager? {
//        return authentication
//    }
//    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
//        val credentials = ObjectMapper().readValue(request.inputStream, Credentials::class.java)
//        val authenticationToken = UsernamePasswordAuthenticationToken(credentials.username, credentials.password)
//        return authentication.authenticate(authenticationToken)
//    }

//    override fun successfulAuthentication(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        chain: FilterChain,
//        authResult: Authentication
//    ) {
//        val token = Jwts.builder()
//            .setSubject(authResult.name)
//            .setExpiration(Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
//            .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET.toByteArray())
//            .compact()
//        response.addHeader("Authorization", "Bearer $token")
//    }
//}

//@Component
//class JsonAuthenticationFilter : UsernamePasswordAuthenticationFilter() {
//
//    @Autowired
//    lateinit var authentication: AuthenticationManager
//
//    override fun getAuthenticationManager(): AuthenticationManager? {
//        return authentication
//    }
//
//    @Throws(AuthenticationException::class)
//    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
//        // Parse the JSON request body and extract the username and password
//        val mapper = ObjectMapper()
//        val credentials: Credentials
//        credentials = try {
//            mapper.readValue(request.inputStream, Credentials::class.java)
//        } catch (e: IOException) {
//            throw RuntimeException("Unable to parse JSON request body", e)
//        }
//        val username: String = credentials.username
//        val password: String = credentials.password
//
//        // Create an authentication token
//        val authToken = UsernamePasswordAuthenticationToken(username, password)
//
//        // Authenticate the user
//        return authentication.authenticate(authToken)
//    }
//}
