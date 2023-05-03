package com.example.paymentsv2.robgen.filters

import com.example.paymentsv2.robert.filters.Filter
import com.example.paymentsv2.robert.filters.FilterField
import com.example.paymentsv2.robert.filters.IntFilterField
import java.util.LinkedHashMap
import kotlin.String

public data class RobEmployeeFilter(
  public val id: IntFilterField? = null,
  public val firstName: FilterField? = null,
) : Filter() {
  public companion object {
    public fun fromMap(map: LinkedHashMap<String, LinkedHashMap<String, String>>):
        RobEmployeeFilter {
      val id = map["id"]?.let { LinkedHashMap(it) }?.let {
       Filter().createFilterField(it, IntFilterField::class.java, "id") as IntFilterField?
      }
      val firstName = map["firstName"]?.let { LinkedHashMap(it) }?.let {
       Filter().createFilterField(it, FilterField::class.java, "firstName") as FilterField?
      }
      return RobEmployeeFilter(id, firstName)
    }
  }
}
