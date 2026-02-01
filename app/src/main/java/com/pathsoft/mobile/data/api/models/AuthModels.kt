package com.pathsoft.mobile.data.api.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val message: String?,
    @SerializedName("accessToken")
    val accessToken: String,
    val user: UserResponse
)

data class UserResponse(
    @SerializedName("userID")
    val userID: Int,
    val username: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("userRoles")
    val userRoles: List<UserRole>,
    @SerializedName("userLabs")
    val userLabs: List<UserLab>
)

data class UserRole(
    @SerializedName("roleID")
    val roleID: Int,
    @SerializedName("roleName")
    val roleName: String
)

data class UserLab(
    @SerializedName("labCode")
    val labCode: String,
    @SerializedName("labName")
    val labName: String
)

