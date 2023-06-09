package com.example.paymentsv2.robgen.utils

import com.example.paymentsv2.robert.filters.Filter
import com.example.paymentsv2.robgen.filters.ClubsFilter
import com.example.paymentsv2.robgen.filters.OrdersFilter
import com.example.paymentsv2.robgen.filters.RobAddressFilter
import com.example.paymentsv2.robgen.filters.RobDepartmentFilter
import com.example.paymentsv2.robgen.filters.RobEmployeeFilter
import com.example.paymentsv2.robgen.filters.RobOrganizationsFilter
import com.example.paymentsv2.robgen.filters.VenuesFilter
import java.util.ArrayList
import java.util.LinkedHashMap
import kotlin.String
import kotlin.collections.MutableMap.MutableEntry
import kotlin.collections.Set

public class FilterUtilClass {
  public fun getType(string: String,
      filters: MutableMap.MutableEntry<String, ArrayList<LinkedHashMap<String, LinkedHashMap<String, String>>>>):
      Set<Filter> = when (string) {
    "VenuesFilter" -> filters.value?.map { VenuesFilter.fromMap(it) }?.toSet() ?: setOf()
    "ClubsFilter" -> filters.value?.map { ClubsFilter.fromMap(it) }?.toSet() ?: setOf()
    "OrdersFilter" -> filters.value?.map { OrdersFilter.fromMap(it) }?.toSet() ?: setOf()
    "RobAddressFilter" -> filters.value?.map { RobAddressFilter.fromMap(it) }?.toSet() ?: setOf()
    "RobOrganizationsFilter" -> filters.value?.map { RobOrganizationsFilter.fromMap(it) }?.toSet()
        ?: setOf()
    "RobDepartmentFilter" -> filters.value?.map { RobDepartmentFilter.fromMap(it) }?.toSet() ?:
        setOf()
    "RobEmployeeFilter" -> filters.value?.map { RobEmployeeFilter.fromMap(it) }?.toSet() ?: setOf()
    else -> throw Exception("No class found for $string")
  }
}
