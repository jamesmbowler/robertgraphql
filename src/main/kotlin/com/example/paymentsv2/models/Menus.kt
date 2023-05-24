package com.example.paymentsv2.models

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
class Menus(
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   var id: Long?,
   var isActive: Boolean? = null,
   var name: String?,
   var description: String?,
   @ManyToMany(cascade = [CascadeType.ALL])
   @JoinTable(name = "menus_menu_items")
   var menuItems: Set<MenuItems>? = null,
   @CreationTimestamp
   val createdOn: Instant? = null,

   @UpdateTimestamp
   val updatedOn: Instant? = null,
)
