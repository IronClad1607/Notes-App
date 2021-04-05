package com.ironclad.notesapp.utils.extensions

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("hh:mm','dd MMM ''yy", Locale.UK)
    return sdf.format(Date())
}

fun getNoOfColumns(columnWidth: Int, context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return ((displayMetrics.widthPixels / displayMetrics.density) / columnWidth).toInt()
}