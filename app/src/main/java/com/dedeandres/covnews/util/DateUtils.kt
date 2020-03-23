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