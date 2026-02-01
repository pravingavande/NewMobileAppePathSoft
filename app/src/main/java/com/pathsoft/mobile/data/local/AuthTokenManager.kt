package com.pathsoft.mobile.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthTokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "pathsoft_prefs",
        Context.MODE_PRIVATE
    )
    
    private val KEY_TOKEN = "auth_token"
    private val KEY_USER = "user_data"
    private val KEY_LAB_CODE = "selected_lab_code"
    
    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }
    
    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }
    
    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
    
    fun clearToken() {
        prefs.edit().remove(KEY_TOKEN).apply()
    }
    
    fun saveUserData(userJson: String) {
        prefs.edit().putString(KEY_USER, userJson).apply()
    }
    
    fun getUserData(): String? {
        return prefs.getString(KEY_USER, null)
    }
    
    fun clearUserData() {
        prefs.edit().remove(KEY_USER).apply()
    }
    
    fun saveLabCode(labCode: String) {
        prefs.edit().putString(KEY_LAB_CODE, labCode).apply()
    }
    
    fun getLabCode(): String? {
        return prefs.getString(KEY_LAB_CODE, null)
    }
    
    fun clearAll() {
        prefs.edit().clear().apply()
    }
}

