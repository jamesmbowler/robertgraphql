package com.example.paymentsv2.robgen.filters

import com.example.paymentsv2.robert.filters.Filter
import com.example.paymentsv2.robert.filters.FilterField
import com.example.paymentsv2.robert.filters.IntFilterField

public data class RobAddressFilter(
  public val id: IntFilterField? = null,
  public val street: FilterField? = null,
) : Filter()
{
  companion object {
    fun fromMap(map: LinkedHashMap<String, LinkedHashMap<String, String>>): RobAddressFilter {

      val id = map["id"]?.let { LinkedHashMap(it) }
      val street = map["street"]?.let { LinkedHashMap(it) }

      return RobAddressFilter(
        Filter().createFilterField(id, IntFilterField::class.java, "id") as IntFilterField?,
        Filter().createFilterField(street, FilterField::class.java, "street"),
      )
    }
  }
}
