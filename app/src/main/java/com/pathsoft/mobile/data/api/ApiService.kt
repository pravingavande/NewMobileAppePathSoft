package com.pathsoft.mobile.data.api

import com.pathsoft.mobile.data.api.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Authentication
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    
    // Patient Management
    @GET("patients/patients/list")
    suspend fun getPatientsList(
        @Query("labCode") labCode: String,
        @Query("filter") filter: String? = null,
        @Query("search") search: String? = null,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10,
        @Query("fromDate") fromDate: String? = null,
        @Query("toDate") toDate: String? = null
    ): Response<PatientsListResponse>
    
    @GET("patients/{patientId}/with-tests")
    suspend fun getPatientWithTests(
        @Path("patientId") patientId: Int,
        @Query("labCode") labCode: String,
        @Query("visitId") visitId: Int? = null
    ): Response<PatientWithTestsResponse>
    
    @GET("patient-registration/search-patient")
    suspend fun searchPatient(
        @Query("name") name: String? = null,
        @Query("mobile") mobile: String? = null,
        @Query("labCode") labCode: String
    ): Response<List<PatientSearchResult>>
    
    @POST("patient-registration/save")
    suspend fun registerPatient(@Body request: PatientRegistrationRequest): Response<Any>
    
    // Test Management
    @GET("subdoctorm/active")
    suspend fun getActiveTests(
        @Query("labCode") labCode: String
    ): Response<List<TestResponse>>
    
    @GET("Package/active")
    suspend fun getActivePackages(
        @Query("labCode") labCode: String
    ): Response<List<PackageResponse>>
    
    @GET("Package/{packageId}/tests")
    suspend fun getPackageTests(
        @Path("packageId") packageId: Int,
        @Query("labCode") labCode: String
    ): Response<PackageTestsResponse>
    
    @GET("test/GetTestParameters")
    suspend fun getTestParameters(
        @Query("sidList") sidList: String,
        @Query("gid") gid: Int? = null,
        @Query("letter") letter: String? = null,
        @Query("visitId") visitId: Int? = null,
        @Query("patientId") patientId: Int? = null,
        @Query("labCode") labCode: String
    ): Response<TestParametersResponse>
    
    // Visit Workspace
    @GET("visit-workspace/{visitId}/{labCode}")
    suspend fun getVisitWorkspace(
        @Path("visitId") visitId: Int,
        @Path("labCode") labCode: String
    ): Response<VisitWorkspaceResponse>
    
    @PUT("visit-workspace/update-patient")
    suspend fun updatePatient(@Body request: UpdatePatientRequest): Response<Any>
    
    @POST("visit-workspace/add-tests")
    suspend fun addTests(@Body request: AddTestsRequest): Response<Any>
    
    @DELETE("visit-workspace/remove-test/{pvtID}")
    suspend fun removeTest(
        @Path("pvtID") pvtID: Int,
        @Body request: RemoveTestRequest
    ): Response<Any>
    
    @POST("visit-workspace/recalculate-billing")
    suspend fun recalculateBilling(@Body request: RecalculateBillingRequest): Response<Any>
    
    @POST("visit-workspace/apply-discount")
    suspend fun applyDiscount(@Body request: ApplyDiscountRequest): Response<Any>
    
    @POST("visit-workspace/add-payment")
    suspend fun addPayment(@Body request: AddPaymentRequest): Response<Any>
    
    @POST("visit-workspace/collect-sample")
    suspend fun collectSample(@Body request: CollectSampleRequest): Response<Any>
    
    // Test Results
    @GET("TestResults/ByPatientVisitWithPending")
    suspend fun getTestResults(
        @Query("patientId") patientId: Int,
        @Query("visitId") visitId: Int,
        @Query("labCode") labCode: String
    ): Response<TestResultsResponse>
    
    @POST("TestResults/save")
    suspend fun saveTestResults(@Body request: SaveTestResultsRequest): Response<Any>
    
    @POST("TestResults/approve")
    suspend fun approveTestResults(@Body request: ApproveTestResultsRequest): Response<Any>
    
    @GET("TestResults/counts")
    suspend fun getApprovalCounts(
        @Query("labCode") labCode: String
    ): Response<ApprovalCountsResponse>
    
    // Reports
    @GET("reports/GeneratePathologyReport")
    suspend fun generatePathologyReport(
        @Query("patientId") patientId: Int,
        @Query("visitId") visitId: Int,
        @Query("labCode") labCode: String,
        @Query("showHeader") showHeader: Boolean = true,
        @Query("showFooter") showFooter: Boolean = true,
        @Query("showSignature") showSignature: Boolean = true,
        @Query("isLandscape") isLandscape: Boolean = false,
        @Query("sidList") sidList: String? = null,
        @Query("printMode") printMode: String? = null
    ): Response<okhttp3.ResponseBody>
}

