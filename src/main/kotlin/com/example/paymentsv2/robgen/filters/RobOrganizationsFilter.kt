package com.example.paymentsv2.robgen.filters

import com.example.paymentsv2.robert.filters.Filter
import com.example.paymentsv2.robert.filters.FilterField
import com.example.paymentsv2.robert.filters.IntFilterField
import java.util.LinkedHashMap
import kotlin.String

public data class RobOrganizationsFilter(
  public val id: IntFilterField? = null,
  public val name: FilterField? = null,
) : Filter() {
  public companion object {
    public fun fromMap(map: LinkedHashMap<String, LinkedHashMap<String, String>>):
        RobOrganizationsFilter {
      val id = map["id"]?.let { LinkedHashMap(it) }?.let {
       Filter().createFilterField(it, IntFilterField::class.java, "id") as IntFilterField?
      }
      val name = map["name"]?.let { LinkedHashMap(it) }?.let {
       Filter().createFilterField(it, FilterField::class.java, "name") as FilterField?
      }
      return RobOrganizationsFilter(id, name)
    }
  }
}
