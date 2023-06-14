package com.example.paymentsv2.robgen.filters

import com.example.paymentsv2.robert.filters.EnumFilterField
import com.example.paymentsv2.robert.filters.Filter
import com.example.paymentsv2.robert.filters.IntFilterField
import com.example.paymentsv2.robert.utils.OrderStatus

public data class OrdersFilter(
  public val id: IntFilterField? = null,
  public val status: EnumFilterField<OrderStatus>? = null,
  //public val name: FilterField? = null,
  public val userId: IntFilterField? = null,
) : Filter() {
  public companion object {
    public fun fromMap(map: LinkedHashMap<String, LinkedHashMap<String, String>>): OrdersFilter {
      val id = map["id"]?.let { LinkedHashMap(it) }?.let {
       Filter().createFilterField(it, IntFilterField::class.java, "id") as IntFilterField?
      }
      val userId = map["userId"]?.let { LinkedHashMap(it) }?.let {
       Filter().createFilterField(it, IntFilterField::class.java, "name") as IntFilterField?
      }

      val status = map["status"]?.let { LinkedHashMap(it) }?.let {
       Filter().createFilterField(it, EnumFilterField::class.java, "status") as EnumFilterField<OrderStatus>?
      }
      return OrdersFilter(id, status, userId)
    }
  }
}
