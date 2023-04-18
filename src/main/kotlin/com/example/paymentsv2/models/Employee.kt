package com.example.paymentsv2.models

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
class Employee(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  public var id: Long?,
  @JsonProperty("firstName")
  public var firstName: String,
  @JsonProperty("lastName") var lastName: String,
  @JsonProperty("position") var position: String,

  @ManyToMany(mappedBy = "employees")
  //@JoinColumn(name = "department_id")
  var departments: Set<Department>? = null,

  @ManyToMany
  //@JoinTable(name="address")
  //@JoinColumn(name = "employee_id")
  var addresses: Set<Address?>? = null,

  @JsonProperty("salary") var salary: Int? = null,
  @JsonProperty("age") var age: Int? = null,
)