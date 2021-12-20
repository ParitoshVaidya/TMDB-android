package com.supersnippets.tmdb.helpers

import java.text.SimpleDateFormat
import java.util.*

fun getDuration(minuteCount: Int): String {
    val hours: Int = minuteCount / 60
    val minutes: Int = minuteCount % 60
    return String.format("%dh %02dmin", hours, minutes)
}

fun convertDate(strDate: String): String {
    val fromDateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
    val date = fromDateFormat.parse(strDate)!!
    val toDateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    return toDateFormat.format(date)
}