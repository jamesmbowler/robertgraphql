package com.example.paymentsv2.controllers

import com.example.paymentsv2.dtos.UserModel
import com.example.paymentsv2.models.User
import com.example.paymentsv2.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping


@Controller
class AuthController @Autowired constructor(
    userService: UserService,
    val authenticationManager: AuthenticationManager
) {

//    @Autowired
//    private val authenticationManager: AuthenticationManager? = null

    private val userService: UserService
    //private val authenticationManager: authManagerBuilderApi


    init {
        this.userService = userService
        //this.authenticationManager = authenticationManager
    }

    @GetMapping("index")
    fun home(): String {
        return "index"
    }

    @GetMapping("/login")
    fun loginForm(): String {
        return "login"
    }

    // handler method to handle user registration request
    @GetMapping("register")
    fun showRegistrationForm(model: Model): String {
        val user = UserModel()
        model.addAttribute("user", user)
        return "register"
    }

//    @PostMapping("/login")
//    fun processLogin(request: HttpServletRequest): String {
//        val username = request.getParameter("username")
//        val password = request.getParameter("password")
//
//        val authenticationToken: Authentication = UsernamePasswordAuthenticationToken(username, password)
//        val authentication: Authentication = authenticationManager.authenticate(authenticationToken)
//
//        if (authentication.isAuthenticated) {
//            SecurityContextHolder.getContext().authentication = authentication
//            return "redirect:/users" // Replace "/users" with the desired URL after successful login
//        } else {
//            // Authentication failed, redirect back to the login page with an error message
//            return "redirect:/login?error"
//        }
//    }
//    @PostMapping("/login")
//    fun login(@RequestBody credentials: Credentials): ResponseEntity<*> {
//        val authenticationToken = UsernamePasswordAuthenticationToken(credentials.username, credentials.password)
//        val authentication = authenticationManager.authenticate(authenticationToken)
//        SecurityContextHolder.getContext().authentication = authentication
//        return ResponseEntity.ok("Authenticated")
//    }

//    @PostMapping("/login_m")
//    fun authenticateUser(@RequestBody loginDto: UserModel): ResponseEntity<String?>? {
//        val authentication: Authentication = authenticationManager.authenticate(
//            UsernamePasswordAuthenticationToken(
//                loginDto.email, loginDto.password
//            )
//        )
//        SecurityContextHolder.getContext().authentication = authentication
//        return ResponseEntity("User signed-in successfully!.", HttpStatus.OK)
//    }
//
//    @Autowired
//    private val userDetailsService: UserDetailsService? = null
//
//    @Bean
//    fun authManagerBuilderApi(@Autowired auth: AuthenticationManagerBuilder): AuthenticationManagerBuilder? {
//        auth.userDetailsService<UserDetailsService>(userDetailsService).passwordEncoder(SpringSecurity.passwordEncoder())
//        return auth
//    }
//
//    @Autowired
//    fun configureGlobal(auth: AuthenticationManagerBuilder) {
//        auth
//            .userDetailsService(userDetailsService)
//            .passwordEncoder(SpringSecurity.passwordEncoder())
//    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    fun registration(
        @Valid @ModelAttribute("user") user: UserModel,
        result: BindingResult,
        model: Model
    ): String {
        val existing: User? = userService.findByEmail(user.email.toString().trim())
        if (existing != null) {
            result.rejectValue("email", "email error", "There is already an account registered with that email")
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user)
            return "register"
        }
        userService.saveUser(user)
        return "redirect:/register?success"
    }

    @GetMapping("/users")
    fun listRegisteredUsers(model: Model): String {
        val users: List<UserModel?>? = userService.findAllUsers()
        model.addAttribute("users", users)
        return "users"
    }
}

data class Credentials (
    val username: String = "",
    val password: String = ""
)
//
//class MyCustomDsl : AbstractHttpConfigurer<MyCustomDsl?, HttpSecurity?>() {
//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
//        val authenticationManager = http.getSharedObject(
//            AuthenticationManager::class.java
//        )
//        http.addFilter(CustomFilter(authenticationManager))
//    }
//
//    companion object {
//        fun customDsl(): MyCustomDsl {
//            return MyCustomDsl()
//        }
//    }
//}