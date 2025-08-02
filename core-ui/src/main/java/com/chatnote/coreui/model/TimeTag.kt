package com.chatnote.coreui.model

import java.util.Calendar

enum class TimeTag(val label: String, val emoji: String) {
    MORNING(label = "Morning", emoji = "☀️"),
    AFTERNOON(label = "Afternoon", emoji = "🌤️"),
    NIGHT(label = "Night", emoji = "🌙"),

    CHRISTMAS(label = "Christmas", emoji = "🎄"),
    NEW_YEAR(label = "New Year", emoji = "🎆"),
    HALLOWEEN(label = "Halloween", emoji = "🎃"),
    VALENTINES_DAY(label = "Valentine's Day", emoji = "❤️"),
    EARTH_DAY(label = "Earth Day", emoji = "🌍");

    companion object {
        fun fromDate(millis: Long): TimeTag {
            val calendar = Calendar.getInstance().apply { timeInMillis = millis }
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)

            return when {
                day == 25 && month == Calendar.DECEMBER -> CHRISTMAS
                day == 1 && month == Calendar.JANUARY -> NEW_YEAR
                day == 31 && month == Calendar.OCTOBER -> HALLOWEEN
                day == 14 && month == Calendar.FEBRUARY -> VALENTINES_DAY
                day == 22 && month == Calendar.APRIL -> EARTH_DAY
                hour in 5..11 -> MORNING
                hour in 12..17 -> AFTERNOON
                else -> NIGHT
            }
        }
    }
}