package com.dzul.apps.subs3aplikasigithubuserdzulfikar.preference

import android.content.Context
import androidx.core.content.edit
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.data.PreferencesItem

class AppPreferences(context: Context) {
    companion object {
        private const val PREFS_NAME = "app_pref"
        private const val TIME = "time"
        private const val REMINDER_SET = "is_set"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setPreferences(value: PreferencesItem){
        preferences.edit {
            putString(TIME, value.time)
            putBoolean(REMINDER_SET, value.isSet)
        }
    }

    fun getPreferences(): PreferencesItem {
        val prefs = PreferencesItem()
        prefs.time = preferences.getString(TIME, null)
        prefs.isSet = preferences.getBoolean(REMINDER_SET, false)

        return prefs
    }
}