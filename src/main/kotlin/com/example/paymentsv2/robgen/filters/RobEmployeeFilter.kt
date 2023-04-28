package com.example.paymentsv2.robgen.filters

import com.example.paymentsv2.robert.filters.Filter
import com.example.paymentsv2.robert.filters.FilterField
import com.example.paymentsv2.robert.filters.IntFilterField

public data class RobEmployeeFilter(
  public val id: IntFilterField? = null,
  public val firstName: FilterField? = null,
) : Filter()
{
  companion object {
    fun fromMap(map: LinkedHashMap<String, LinkedHashMap<String, String>>): RobEmployeeFilter {

      val id = map["id"]?.let { LinkedHashMap(it) }
      val firstName = map["firstName"]?.let { LinkedHashMap(it) }

      return RobEmployeeFilter(
        Filter().createFilterField(id, IntFilterField::class.java, "id") as IntFilterField?,
        Filter().createFilterField(firstName, FilterField::class.java, "firstName"),
      )
    }
  }
}
