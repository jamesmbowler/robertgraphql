package com.example.paymentsv2.utils

class Utils {
    fun linkedHashMapToString(map: LinkedHashMap<*, *>): String {
        val sb = StringBuilder("{")
        for ((key, value) in map) {
            sb.append("\"$key\": ")
            if (value is LinkedHashMap<*, *>) {
                sb.append(linkedHashMapToString(value))
            } else {
                sb.append("\"$value\"")
            }
            sb.append(", ")
        }
        if (sb.length > 1) {
            sb.setLength(sb.length - 2)
        }
        sb.append("}")
        return sb.toString()
    }
}