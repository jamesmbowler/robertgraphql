package com.example.paymentsv2.controllers

import com.example.paymentsv2.dtos.UserModel
import com.example.paymentsv2.models.User
import com.example.paymentsv2.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping


@Controller
class AuthController @Autowired constructor(
    userService: UserService,
    //val authenticationManager: AuthenticationManager
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

    // handler method to handle register user form submit request
//    @PostMapping("/register/save")
//    fun registration(
//        @Valid @ModelAttribute("user") user: UserModel,
//        result: BindingResult,
//        model: Model
//    ): String {
//        val existing: User? = userService.findByEmail(user.email.toString().trim())
//        if (existing != null) {
//            result.rejectValue("email", "email error", "There is already an account registered with that email")
//        }
//        if (result.hasErrors()) {
//            model.addAttribute("user", user)
//            return "register"
//        }
//        userService.saveUser(user)
//        return "redirect:/register?success"
//    }

    @PostMapping("/adduser")
    fun adduser(
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
            return "newuser"
        }
        userService.saveUser(user)
        return "redirect:/newuser?success"
    }

    @GetMapping("/users")
    fun listRegisteredUsers(model: Model): String {
        val users: List<UserModel?>? = userService.findAllUsers()
        model.addAttribute("users", users)

        val user = UserModel()
        model.addAttribute("user", user)

        return "users"
    }
}

data class Credentials (
    val username: String = "",
    val password: String = ""
)