package com.example.paymentsv2.models

import jakarta.persistence.*

@Entity
class Organization(
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   var id: Long?,
   var isActive: Boolean? = null,
   var name: String?,
   @ManyToMany
   val departments: Set<Department?>? = null
)
