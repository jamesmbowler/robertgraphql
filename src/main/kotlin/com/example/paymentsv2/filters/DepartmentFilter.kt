package com.example.paymentsv2.filters

import com.fasterxml.jackson.annotation.JsonProperty

data class DepartmentFilter(
  @JsonProperty("id")
  var id: IntFilterField? = IntFilterField(),
  @JsonProperty("name")
  var name: FilterField? = FilterField()
): Filter()