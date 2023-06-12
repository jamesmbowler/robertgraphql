package com.example.paymentsv2.models

import com.example.paymentsv2.dtos.UserDto
import jakarta.persistence.*

@Table(name = "users")
@Entity
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String? = null,

    @Column(nullable = false, unique = true)
    var email: String? = null,

    @Column(nullable = false)
    var password: String? = null,

    //for sending push notifications
    @Column(nullable = true)
    var fcmToken: String? = "",

    //for sending push notifications
    @Column(nullable = true)
    var memberId: Long? =null,

    @OneToMany(mappedBy = "user")
    var orders: List<Orders> = ArrayList(),

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "USER_ID", referencedColumnName = "ID")],
        inverseJoinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")]
    )
    var roles: List<Role> = ArrayList(),
) {
    companion object {
        private const val serialVersionUID = 1L
    }
    fun toDto() = UserDto(
        id = id,
        name = name,
        email = email,
        roles = roles,
    )
}
