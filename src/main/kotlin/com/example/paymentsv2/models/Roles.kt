package com.example.paymentsv2.models

import jakarta.persistence.*

@Entity
@Table(name = "roles")
class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    //@Column(nullable = false, unique = true
    @Enumerated(EnumType.STRING)
    val name: RolesEnums? = null,

    @ManyToMany(mappedBy = "roles")
    private val users: List<User>? = null
)

enum class RolesEnums {
    ROLE_USER,
    ROLE_ADMIN
}