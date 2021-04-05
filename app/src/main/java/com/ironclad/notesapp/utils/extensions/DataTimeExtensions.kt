package com.ironclad.notesapp.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("hh:mm','dd MMM ''yy", Locale.UK)
    return sdf.format(Date())
}