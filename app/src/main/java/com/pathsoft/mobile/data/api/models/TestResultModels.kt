package com.pathsoft.mobile.data.api.models

import com.google.gson.annotations.SerializedName

data class TestResultsResponse(
    val success: Boolean,
    @SerializedName("resultHeaderID")
    val resultHeaderID: Int,
    val patient: Patient,
    val tests: List<TestResult>
)

data class TestResult(
    @SerializedName("sid")
    val sid: Int,
    @SerializedName("sname")
    val sname: String,
    @SerializedName("resultHeaderID")
    val resultHeaderID: Int?,
    val parameters: List<TestResultParameter>
)

data class TestResultParameter(
    @SerializedName("parameterID")
    val parameterID: Int,
    @SerializedName("parameterName")
    val parameterName: String,
    val value: String?,
    @SerializedName("normalRangeMin")
    val normalRangeMin: Double?,
    @SerializedName("normalRangeMax")
    val normalRangeMax: Double?,
    val unit: String?,
    @SerializedName("isAbnormal")
    val isAbnormal: Boolean?
)

data class SaveTestResultsRequest(
    @SerializedName("labCode")
    val labCode: String,
    @SerializedName("patientID")
    val patientID: Int,
    @SerializedName("visitID")
    val visitID: Int,
    val tests: List<TestResultSave>
)

data class TestResultSave(
    @SerializedName("sid")
    val sid: Int,
    val parameters: List<TestResultParameterSave>
)

data class TestResultParameterSave(
    @SerializedName("parameterID")
    val parameterID: Int,
    val value: String?
)

data class ApproveTestResultsRequest(
    @SerializedName("labCode")
    val labCode: String,
    @SerializedName("resultHeaderID")
    val resultHeaderID: Int,
    @SerializedName("approvedByUserID")
    val approvedByUserID: Int,
    @SerializedName("approvedByUserName")
    val approvedByUserName: String,
    val approve: Boolean,
    val remarks: String
)

data class ApprovalCountsResponse(
    val success: Boolean,
    val pending: Int,
    val completed: Int
)

