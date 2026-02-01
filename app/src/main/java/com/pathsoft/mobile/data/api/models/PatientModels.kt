package com.pathsoft.mobile.data.api.models

import com.google.gson.annotations.SerializedName

data class PatientsListResponse(
    val success: Boolean,
    val data: List<PatientView>,
    @SerializedName("totalCount")
    val totalCount: Int,
    val page: Int,
    @SerializedName("pageSize")
    val pageSize: Int
)

data class PatientView(
    @SerializedName("patientID")
    val patientID: Int,
    @SerializedName("patientName")
    val patientName: String,
    @SerializedName("mobileNo")
    val mobileNo: String?,
    @SerializedName("visitID")
    val visitID: Int,
    @SerializedName("visitStatus")
    val visitStatus: String?,
    @SerializedName("visitDate")
    val visitDate: String?,
    @SerializedName("totalAmount")
    val totalAmount: Double?,
    @SerializedName("paidAmount")
    val paidAmount: Double?,
    @SerializedName("balanceAmount")
    val balanceAmount: Double?
)

data class Patient(
    @SerializedName("patientID")
    val patientID: Int,
    @SerializedName("patientName")
    val patientName: String,
    @SerializedName("mobileNo")
    val mobileNo: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String?,
    @SerializedName("age")
    val age: Int?,
    @SerializedName("ageText")
    val ageText: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("genderID")
    val genderID: Int?
)

data class PatientSearchResult(
    @SerializedName("pid")
    val pid: Int,
    @SerializedName("patientName")
    val patientName: String,
    @SerializedName("mobileNo")
    val mobileNo: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("age")
    val age: Int?,
    @SerializedName("agetext")
    val agetext: String?
)

data class PatientWithTestsResponse(
    val patient: Patient,
    val visit: Visit?,
    val tests: List<Test>
)

data class Visit(
    @SerializedName("visitID")
    val visitID: Int,
    @SerializedName("visitDate")
    val visitDate: String?,
    @SerializedName("visitStatus")
    val visitStatus: String?,
    @SerializedName("sampleRegistrationDateTime")
    val sampleRegistrationDateTime: String?,
    @SerializedName("refDoctorID")
    val refDoctorID: Int?,
    @SerializedName("sampleCollectionLocationID")
    val sampleCollectionLocationID: Int?
)

data class Test(
    @SerializedName("sid")
    val sid: Int,
    @SerializedName("sname")
    val sname: String,
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("tat")
    val tat: String?,
    @SerializedName("sampleProcessingAt")
    val sampleProcessingAt: String?,
    @SerializedName("sampleTypeId")
    val sampleTypeId: Int?,
    @SerializedName("sampleTypeName")
    val sampleTypeName: String?
)

data class PatientRegistrationRequest(
    @SerializedName("labCode")
    val labCode: String,
    val title: String?,
    @SerializedName("patientName")
    val patientName: String,
    @SerializedName("mobileNo")
    val mobileNo: String?,
    val email: String?,
    val address: String?,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String?,
    val age: Int?,
    @SerializedName("ageText")
    val ageText: String?,
    @SerializedName("ageYear")
    val ageYear: Int?,
    @SerializedName("ageMonth")
    val ageMonth: Int?,
    @SerializedName("ageDay")
    val ageDay: Int?,
    val gender: String?,
    @SerializedName("genderID")
    val genderID: Int?,
    @SerializedName("refDoctorID")
    val refDoctorID: Int?,
    @SerializedName("sampleCollectionLocationID")
    val sampleCollectionLocationID: Int?,
    @SerializedName("sampleRegistrationDateTime")
    val sampleRegistrationDateTime: String,
    @SerializedName("testSIDs")
    val testSIDs: List<Int>,
    @SerializedName("packageIDs")
    val packageIDs: List<Int>?,
    @SerializedName("createdBy")
    val createdBy: String
)

