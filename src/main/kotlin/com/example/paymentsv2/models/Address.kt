package com.example.paymentsv2.models

import jakarta.persistence.*

@Entity
class Address(
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   var id: Long?,
   var isActive: Boolean? = null,
   var street: String,

   @ManyToMany(mappedBy="addresses")
   //@JoinColumn(name="employee_id")
   var employees: Set<Employee>? = null
)
