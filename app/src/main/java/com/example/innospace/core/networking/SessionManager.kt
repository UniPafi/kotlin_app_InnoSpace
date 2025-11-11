package com.example.innospace.core.networking

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val AUTH_TOKEN = "auth_token"
        private const val USER_ID = "user_id"
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(AUTH_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(AUTH_TOKEN, null)
    }

    fun saveUserId(userId: Long) {
        val editor = prefs.edit()
        editor.putLong(USER_ID, userId)
        editor.apply()
    }

    fun saveUserSession(userId: Long, token: String) {
        val editor = prefs.edit()
        editor.putLong(USER_ID, userId)
        editor.putString(AUTH_TOKEN, token)
        editor.apply()
    }

    fun getUserId(): Long {
        return prefs.getLong(USER_ID, -1L)
    }


    fun isLoggedIn(): Boolean {
        return fetchAuthToken() != null && getUserId() != -1L
    }

    fun clearAuthToken() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}