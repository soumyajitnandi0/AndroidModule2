package com.example.chatappfirebase

import android.content.Context
import com.google.gson.Gson

object SharedPrefHelper {
    private const val PREFS_NAME = "ChatAppPrefs"
    private const val USER_KEY = "User"

    fun saveUser(context: Context, user: UserModel) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        prefs.putString(USER_KEY, Gson().toJson(user)).apply()
    }

    fun getUser(context: Context): UserModel? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val userJson = prefs.getString(USER_KEY, null)
        return Gson().fromJson(userJson, UserModel::class.java)
    }

    fun clearUser(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().clear().apply()
    }
}
