package com.pathsoft.mobile.data.repository

import com.pathsoft.mobile.data.api.ApiService
import com.pathsoft.mobile.data.api.models.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VisitWorkspaceRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getVisitWorkspace(
        visitId: Int,
        labCode: String
    ): Result<VisitWorkspaceData> {
        return try {
            val response = apiService.getVisitWorkspace(visitId, labCode)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch visit workspace"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updatePatient(request: UpdatePatientRequest): Result<Unit> {
        return try {
            val response = apiService.updatePatient(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message() ?: "Update failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun addTests(request: AddTestsRequest): Result<Unit> {
        return try {
            val response = apiService.addTests(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to add tests"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun removeTest(pvtID: Int, request: RemoveTestRequest): Result<Unit> {
        return try {
            val response = apiService.removeTest(pvtID, request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to remove test"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun recalculateBilling(request: RecalculateBillingRequest): Result<Unit> {
        return try {
            val response = apiService.recalculateBilling(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to recalculate billing"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun applyDiscount(request: ApplyDiscountRequest): Result<Unit> {
        return try {
            val response = apiService.applyDiscount(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to apply discount"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun addPayment(request: AddPaymentRequest): Result<Unit> {
        return try {
            val response = apiService.addPayment(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to add payment"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun collectSample(request: CollectSampleRequest): Result<Unit> {
        return try {
            val response = apiService.collectSample(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to collect sample"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

