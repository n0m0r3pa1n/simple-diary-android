package com.nmp90.mysimplediary.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toSimpleString() : String {
    val format = SimpleDateFormat("dd.MM", Locale.getDefault())
    return format.format(this)
}