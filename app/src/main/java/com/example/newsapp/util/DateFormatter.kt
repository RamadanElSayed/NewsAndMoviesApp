package com.example.newsapp.util

import androidx.compose.runtime.Composable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@Composable
fun dateFormatter(inputDateTime: String?, locale: Locale = Locale.getDefault()): String {
    // Define input and output formatters
    val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(locale)

    // Format date or return fallback message if parsing fails
    return try {
        val dateTime = OffsetDateTime.parse(inputDateTime, inputFormatter)
        dateTime.format(outputFormatter)
    } catch (e: Exception) {
        "Invalid date"
    }
}
