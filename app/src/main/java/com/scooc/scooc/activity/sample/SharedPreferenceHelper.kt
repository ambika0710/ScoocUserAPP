package com.scooc.scooc.activity.sample

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper constructor(
    context: Context
) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

    var startDateDay: Int
        get() = preferences.getInt(START_DATE_DAY, 0)
        set(startDateDay) = preferences.edit().putInt(START_DATE_DAY, startDateDay).apply()

    var startDateMonth: Int
        get() = preferences.getInt(START_DATE_MONTH, 0)
        set(startDateMonth) = preferences.edit().putInt(START_DATE_MONTH, startDateMonth).apply()

    var startDateYear: Int
        get() = preferences.getInt(START_DATE_YEAR, 0)
        set(startDateYear) = preferences.edit().putInt(START_DATE_YEAR, startDateYear).apply()

    var endDateDay: Int
        get() = preferences.getInt(END_DATE_DAY, 0)
        set(endDateDay) = preferences.edit().putInt(END_DATE_DAY, endDateDay).apply()

    var endDateMonth: Int
        get() = preferences.getInt(END_DATE_MONTH, 0)
        set(endDateMonth) = preferences.edit().putInt(END_DATE_MONTH, endDateMonth).apply()

    var endDateYear: Int
        get() = preferences.getInt(END_DATE_YEAR, 0)
        set(endDateYear) = preferences.edit().putInt(END_DATE_YEAR, endDateYear).apply()

    var timeHour: Int
        get() = preferences.getInt(TIME_HOUR, 0)
        set(timeHour) = preferences.edit().putInt(TIME_HOUR, timeHour).apply()

    var timeMinute: Int
        get() = preferences.getInt(TIME_MINUTE, 0)
        set(timeMinute) = preferences.edit().putInt(TIME_MINUTE, timeMinute).apply()


    fun removeSharedPreference() {
        preferences.edit().clear().apply()
    }

    fun clearSharedPreference() {
        removeSharedPreference()
    }

    companion object {
        const val SHARED_PREFERENCE_NAME = "SharedPreference"
        const val START_DATE_DAY = "startDateDay"
        const val START_DATE_MONTH = "startDateMonth"
        const val START_DATE_YEAR = "startDateYear"
        const val END_DATE_DAY = "endDateDay"
        const val END_DATE_MONTH = "endDateMonth"
        const val END_DATE_YEAR = "endDateYear"
        const val TIME_HOUR = "timeHour"
        const val TIME_MINUTE = "timeMinute"
    }

}
