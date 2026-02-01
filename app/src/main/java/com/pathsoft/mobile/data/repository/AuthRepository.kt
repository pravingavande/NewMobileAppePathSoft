package com.pathsoft.mobile.data.repository

import com.pathsoft.mobile.data.api.ApiService
import com.pathsoft.mobile.data.api.models.LoginRequest
import com.pathsoft.mobile.data.api.models.LoginResponse
import com.pathsoft.mobile.data.local.AuthTokenManager
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val authTokenManager: AuthTokenManager,
    private val gson: Gson
) {
    suspend fun login(username: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(username, password))
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                authTokenManager.saveToken(loginResponse.accessToken)
                authTokenManager.saveUserData(gson.toJson(loginResponse.user))
                Result.success(loginResponse)
            } else {
                Result.failure(Exception(response.message() ?: "Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun logout() {
        authTokenManager.clearAll()
    }
    
    fun isLoggedIn(): Boolean {
        return authTokenManager.getToken() != null
    }
}

