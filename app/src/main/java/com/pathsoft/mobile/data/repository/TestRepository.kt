package com.pathsoft.mobile.data.repository

import com.pathsoft.mobile.data.api.ApiService
import com.pathsoft.mobile.data.api.models.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getActiveTests(labCode: String): Result<List<TestResponse>> {
        return try {
            val response = apiService.getActiveTests(labCode)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch tests"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getActivePackages(labCode: String): Result<List<PackageResponse>> {
        return try {
            val response = apiService.getActivePackages(labCode)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch packages"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPackageTests(
        packageId: Int,
        labCode: String
    ): Result<PackageTestsResponse> {
        return try {
            val response = apiService.getPackageTests(packageId, labCode)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch package tests"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getTestParameters(
        sidList: String,
        gid: Int? = null,
        letter: String? = null,
        visitId: Int? = null,
        patientId: Int? = null,
        labCode: String
    ): Result<TestParametersResponse> {
        return try {
            val response = apiService.getTestParameters(
                sidList, gid, letter, visitId, patientId, labCode
            )
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch test parameters"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

