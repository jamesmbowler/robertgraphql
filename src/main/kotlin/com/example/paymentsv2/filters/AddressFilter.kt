package com.example.paymentsv2.filters

class AddressFilter(
    var id: IntFilterField? = IntFilterField(),
    var street: FilterField? = FilterField()
): Filter()