package com.example.paymentsv2.models

import jakarta.persistence.*

@Entity
class Venues(
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   var id: Long?,
   var isActive: Boolean? = null,
   var name: String?,
   var description: String?,
   @ManyToMany
   val menus: Set<Menus?>? = null
)
