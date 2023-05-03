package com.example.paymentsv2.robgen.filters

import com.example.paymentsv2.robert.filters.Filter
import com.example.paymentsv2.robert.filters.FilterField
import com.example.paymentsv2.robert.filters.IntFilterField
import java.util.LinkedHashMap
import kotlin.String

public data class RobDepartmentFilter(
  public val id: IntFilterField? = null,
  public val name: FilterField? = null,
  public val description: FilterField? = null,
) : Filter() {
  public companion object {
    public fun fromMap(map: LinkedHashMap<String, LinkedHashMap<String, String>>):
        RobDepartmentFilter {
      val id = map["id"]?.let { LinkedHashMap(it) }?.let {
       Filter().createFilterField(it, IntFilterField::class.java, "id") as IntFilterField?
      }
      val name = map["name"]?.let { LinkedHashMap(it) }?.let {
       Filter().createFilterField(it, FilterField::class.java, "name") as FilterField?
      }
      val description = map["description"]?.let { LinkedHashMap(it) }?.let {
       Filter().createFilterField(it, FilterField::class.java, "description") as FilterField?
      }
      return RobDepartmentFilter(id, name, description)
    }
  }
}
