package com.chatnote.coreui.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class DateFormatter @Inject constructor() {

    private val dateFormat: SimpleDateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
    private val timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val fullDateFormat: SimpleDateFormat =
        SimpleDateFormat("yyyy MMM dd", Locale.getDefault())

    fun formatDate(millis: Long): String {
        val date = Date(millis)
        return dateFormat.format(date)
    }

    fun formatTime(millis: Long): String {
        val date = Date(millis)
        return timeFormat.format(date)
    }

    fun formatFullDate(millis: Long): String {
        val date = Date(millis)
        return fullDateFormat.format(date)
    }

    fun formatShort(millis: Long): String {
        val currentDate = Calendar.getInstance()
        val noteDate = Calendar.getInstance()
        noteDate.timeInMillis = millis

        return when {
            isToday(currentDate, noteDate) -> formatTime(millis)
            isThisYear(currentDate, noteDate) -> formatDate(millis)
            else -> formatFullDate(millis)
        }
    }


    fun formatLong(millis: Long): String {
        val currentDate = Calendar.getInstance()
        val noteDate = Calendar.getInstance()
        noteDate.timeInMillis = millis

        return when {
            isToday(currentDate, noteDate) -> timeFormat.format(noteDate.time)
            isThisYear(
                currentDate,
                noteDate
            ) -> "${dateFormat.format(noteDate.time)} ${timeFormat.format(noteDate.time)}"

            else -> "${fullDateFormat.format(noteDate.time)} ${timeFormat.format(noteDate.time)}"
        }
    }

    private fun isToday(currentDate: Calendar, noteDate: Calendar): Boolean {
        return currentDate.get(Calendar.YEAR) == noteDate.get(Calendar.YEAR) &&
                currentDate.get(Calendar.MONTH) == noteDate.get(Calendar.MONTH) &&
                currentDate.get(Calendar.DAY_OF_MONTH) == noteDate.get(Calendar.DAY_OF_MONTH)
    }

    private fun isThisYear(currentDate: Calendar, noteDate: Calendar): Boolean {
        return currentDate.get(Calendar.YEAR) == noteDate.get(Calendar.YEAR)
    }
}