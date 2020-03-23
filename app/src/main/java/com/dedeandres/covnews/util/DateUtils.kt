package com.dedeandres.covnews.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.toIndonesiaDateFormat(): String {
    val pattern = "dd MMMM yyyy HH:mm:ss";

    val formatter = SimpleDateFormat(pattern)
    return formatter.format(this)
}

@SuppressLint("SimpleDateFormat")
fun dateSimpleFormat(): Date? {
    val pattern = "yyyy-MM-dd"
    val simpleDateFormat = SimpleDateFormat(pattern)

    val formatter = SimpleDateFormat(pattern)
    return simpleDateFormat.parse(formatter.format(Date()))
}