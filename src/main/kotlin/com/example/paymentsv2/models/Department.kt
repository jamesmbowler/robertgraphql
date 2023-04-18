package com.example.paymentsv2.models

import jakarta.persistence.*

@Entity
class Department(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long?,
  @Column
  val name: String,

  //@OneToMany(mappedBy = "department")
  @ManyToMany
  val employees: Set<Employee?>? = null,

  @ManyToMany(mappedBy = "departments")
  val organizations: Set<Organization?>? = null
)