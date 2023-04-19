package com.example.paymentsv2.filters

data class EmployeeFilter(
  var id: FilterField? = IntFilterField(),
  var firstName: FilterField? = FilterField(),
): Filter()