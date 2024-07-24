package com.hk.listeddashboard.Utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

public fun getDayNameFromDate(dateString: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = dateFormat.parse(dateString)

    val calendar = Calendar.getInstance()
    calendar.time = date

    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    // Map the day of week value to its corresponding name
    val dayNames = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    return dayNames[dayOfWeek - 1]
}

public fun convertDateFormat(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM", Locale.getDefault())

    return try {
        val date = inputFormat.parse(inputDate)
        outputFormat.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}