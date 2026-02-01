package com.pathsoft.mobile.data.repository

import com.pathsoft.mobile.data.api.ApiService
import com.pathsoft.mobile.data.api.models.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getPatientsList(
        labCode: String,
        filter: String? = null,
        search: String? = null,
        page: Int = 1,
        pageSize: Int = 10,
        fromDate: String? = null,
        toDate: String? = null
    ): Result<PatientsListResponse> {
        return try {
            val response = apiService.getPatientsList(
                labCode, filter, search, page, pageSize, fromDate, toDate
            )
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch patients"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPatientWithTests(
        patientId: Int,
        labCode: String,
        visitId: Int? = null
    ): Result<PatientWithTestsResponse> {
        return try {
            val response = apiService.getPatientWithTests(patientId, labCode, visitId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch patient"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun searchPatient(
        name: String? = null,
        mobile: String? = null,
        labCode: String
    ): Result<List<PatientSearchResult>> {
        return try {
            val response = apiService.searchPatient(name, mobile, labCode)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Search failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun registerPatient(request: PatientRegistrationRequest): Result<Unit> {
        return try {
            val response = apiService.registerPatient(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message() ?: "Registration failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

