package com.example.paymentsv2.service

import com.example.paymentsv2.dtos.UserModel
import com.example.paymentsv2.models.Role
import com.example.paymentsv2.models.User
import com.example.paymentsv2.repositories.RoleRepository
import com.example.paymentsv2.repositories.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class UserServiceImpl(
    userRepository: UserRepository,
    roleRepository: RoleRepository,
    passwordEncoder: PasswordEncoder
) : UserService {
    private val userRepository: UserRepository
    private val roleRepository: RoleRepository
    private val passwordEncoder: PasswordEncoder

    init {
        this.userRepository = userRepository
        this.roleRepository = roleRepository
        this.passwordEncoder = passwordEncoder
    }

    @Transactional
    override fun saveUser(userDto: UserModel?) {
        val user = User(
            name = userDto?.firstName + " " + userDto?.lastName,
            email = userDto?.email,
            password = passwordEncoder.encode(userDto?.password)
        )

        var role: Role? = roleRepository.findByName("ROLE_ADMIN")
        if (role == null) {
            role = checkRoleExist()
        }
        user.roles = Arrays.asList(role)
        userRepository.save(user)
    }

    override fun findByEmail(email: String?): User? {
        return userRepository.findByEmail(email)
    }

    override fun findAllUsers(): MutableList<UserModel> {
        val users: MutableList<User?> = userRepository.findAll()
        return users.stream().map {
            convertEntityToDto(
                it!!
            )
        }
            .collect(Collectors.toList<UserModel>())
    }

    private fun convertEntityToDto(user: User): UserModel {

        val name: List<String>? = user.name?.split(" ")
        return UserModel(
            firstName = name?.get(0),
            lastName = name?.get(1),
            email = user.email,
        )
    }

    private fun checkRoleExist(): Role {
        return roleRepository.save(Role(name = "ROLE_ADMIN"))
    }
}