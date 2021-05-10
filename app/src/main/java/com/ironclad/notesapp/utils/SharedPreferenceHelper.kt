package com.ironclad.notesapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper {

    companion object {
        private const val PREF_NAME = "notes app"
        const val USER_LOGGED_IN = "user_logged_in"
        const val LOGGED_IN_SKIPPED = "logged_in_skipped"
        private var prefs: SharedPreferences? = null

        @Volatile
        private var instance: SharedPreferenceHelper? = null


        fun getInstance(context: Context): SharedPreferenceHelper {
            synchronized(this) {
                val _instance = instance
                if (_instance == null) {
                    prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                    instance = _instance
                }
            }
            return SharedPreferenceHelper()
        }
    }

    var userLoggedIn: Boolean
        get() = prefs?.getBoolean(USER_LOGGED_IN, false)!!
        set(value) {
            val edit = prefs?.edit()
            edit?.putBoolean(USER_LOGGED_IN, value)
            edit?.apply()
        }

    var loginSkipped: Boolean
        get() = prefs?.getBoolean(LOGGED_IN_SKIPPED, false)!!
        set(value) {
            val edit = prefs?.edit()
            edit?.putBoolean(LOGGED_IN_SKIPPED, value)
            edit?.apply()
        }
}