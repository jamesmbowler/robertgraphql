package com.example.paymentsv2.filters

data class EmployeeFilter(
  var id: FilterField? = FilterField(),
  var firstName: FilterField? = FilterField(),
): Filter()