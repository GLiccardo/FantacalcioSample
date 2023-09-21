package it.fantacalcio.sample.core.sharedpref

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("SharedPreferencesName", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveLong(key: String, value: Long) {
        editor.putLong(key, value)
        editor.apply()
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun remove(key: String) {
        editor.remove(key)
        editor.apply()
    }

}