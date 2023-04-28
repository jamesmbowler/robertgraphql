package com.example.paymentsv2.robgen.filters

import com.example.paymentsv2.filters.Filter
import com.example.paymentsv2.filters.FilterField
import com.example.paymentsv2.filters.IntFilterField

public data class RobAddressFilter(
  public val id: IntFilterField? = null,
  public val street: FilterField? = null,
) : Filter()
