package global.msnthrp.scanner.utils

import android.content.Context
import android.content.SharedPreferences

object Prefs {

    const val PREF_NAME = "prefs"


    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    private fun readInt(name: String, value: Int) = prefs.getInt(name, value)

    private fun writeInt(name: String, value: Int) {
        prefs.edit().putInt(name, value).apply()
    }

    private fun readBoolean(name: String, value: Boolean) = prefs.getBoolean(name, value)

    private fun writeBoolean(name: String, value: Boolean) {
        prefs.edit().putBoolean(name, value).apply()
    }
}