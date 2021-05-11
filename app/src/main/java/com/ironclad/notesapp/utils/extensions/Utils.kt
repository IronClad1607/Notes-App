package com.ironclad.notesapp.utils.extensions

import android.content.Context
import android.text.Editable
import android.view.View
import android.view.WindowManager
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

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun setUpFullHeight(bottomSheet: View) {
    val layoutParams = bottomSheet.layoutParams
    layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
    bottomSheet.layoutParams = layoutParams
}