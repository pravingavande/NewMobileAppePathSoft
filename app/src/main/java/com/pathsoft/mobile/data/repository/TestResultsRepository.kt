package com.pathsoft.mobile.data.repository

import com.pathsoft.mobile.data.api.ApiService
import com.pathsoft.mobile.data.api.models.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestResultsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getTestResults(
        patientId: Int,
        visitId: Int,
        labCode: String
    ): Result<TestResultsResponse> {
        return try {
            val response = apiService.getTestResults(patientId, visitId, labCode)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch test results"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun saveTestResults(request: SaveTestResultsRequest): Result<Unit> {
        return try {
            val response = apiService.saveTestResults(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to save test results"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun approveTestResults(request: ApproveTestResultsRequest): Result<Unit> {
        return try {
            val response = apiService.approveTestResults(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to approve test results"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getApprovalCounts(labCode: String): Result<ApprovalCountsResponse> {
        return try {
            val response = apiService.getApprovalCounts(labCode)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch approval counts"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

