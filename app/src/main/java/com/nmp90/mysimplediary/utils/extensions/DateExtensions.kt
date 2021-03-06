package com.nmp90.mysimplediary.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toSimpleString() : String {
    val format = SimpleDateFormat("dd.MM yyyy", Locale.getDefault())
    return format.format(this)
}

fun toSimpleStringWithDate(date: Date) : String {
    val format = SimpleDateFormat("dd.MM yyyy", Locale.getDefault())
    return format.format(date)
}