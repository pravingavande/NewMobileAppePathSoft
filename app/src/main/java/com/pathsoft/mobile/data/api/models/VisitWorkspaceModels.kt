package com.pathsoft.mobile.data.api.models

import com.google.gson.annotations.SerializedName

data class VisitWorkspaceResponse(
    val success: Boolean,
    val data: VisitWorkspaceData
)

data class VisitWorkspaceData(
    val visit: Visit,
    val patient: Patient,
    val tests: List<PatientTest>,
    val billing: Billing,
    val doctor: Doctor?,
    @SerializedName("sampleCollectionCenter")
    val sampleCollectionCenter: SampleCollectionCenter?
)

data class PatientTest(
    @SerializedName("pvtID")
    val pvtID: Int,
    @SerializedName("sid")
    val sid: Int,
    @SerializedName("sname")
    val sname: String,
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("discountAmount")
    val discountAmount: Double?,
    @SerializedName("netAmount")
    val netAmount: Double?
)

data class Billing(
    @SerializedName("grossAmount")
    val grossAmount: Double,
    @SerializedName("discountAmount")
    val discountAmount: Double,
    @SerializedName("netAmount")
    val netAmount: Double,
    @SerializedName("paidAmount")
    val paidAmount: Double,
    @SerializedName("balanceAmount")
    val balanceAmount: Double
)

data class Doctor(
    @SerializedName("doctorID")
    val doctorID: Int,
    @SerializedName("doctorName")
    val doctorName: String
)

data class SampleCollectionCenter(
    @SerializedName("locationID")
    val locationID: Int,
    @SerializedName("locationName")
    val locationName: String
)

data class UpdatePatientRequest(
    @SerializedName("visitID")
    val visitID: Int,
    @SerializedName("labCode")
    val labCode: String,
    val title: String?,
    @SerializedName("patientName")
    val patientName: String?,
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
    val sampleRegistrationDateTime: String?,
    @SerializedName("updatedBy")
    val updatedBy: String
)

data class AddTestsRequest(
    @SerializedName("visitID")
    val visitID: Int,
    @SerializedName("labCode")
    val labCode: String,
    @SerializedName("testSIDs")
    val testSIDs: List<Int>,
    @SerializedName("packageIDs")
    val packageIDs: List<Int>?,
    @SerializedName("createdBy")
    val createdBy: String
)

data class RemoveTestRequest(
    @SerializedName("visitID")
    val visitID: Int,
    @SerializedName("labCode")
    val labCode: String,
    @SerializedName("pvtIDs")
    val pvtIDs: List<Int>,
    @SerializedName("updatedBy")
    val updatedBy: String
)

data class RecalculateBillingRequest(
    @SerializedName("VisitID")
    val VisitID: Int,
    @SerializedName("LabCode")
    val LabCode: String,
    @SerializedName("DiscountType")
    val DiscountType: String?,
    @SerializedName("DiscountValue")
    val DiscountValue: Double?,
    @SerializedName("TestIds")
    val TestIds: String?,
    @SerializedName("UpdatedBy")
    val UpdatedBy: String?
)

data class ApplyDiscountRequest(
    @SerializedName("visitID")
    val visitID: Int,
    @SerializedName("labCode")
    val labCode: String,
    @SerializedName("discountType")
    val discountType: String,
    @SerializedName("discountValue")
    val discountValue: Double,
    @SerializedName("discountCategory")
    val discountCategory: String?,
    @SerializedName("doctorDiscount")
    val doctorDiscount: Double?,
    @SerializedName("labDiscount")
    val labDiscount: Double?,
    @SerializedName("writeOff")
    val writeOff: Double?,
    val remarks: String?,
    @SerializedName("updatedBy")
    val updatedBy: String
)

data class AddPaymentRequest(
    @SerializedName("visitID")
    val visitID: Int,
    @SerializedName("labCode")
    val labCode: String,
    @SerializedName("amountPaid")
    val amountPaid: Double,
    @SerializedName("paymentMode")
    val paymentMode: String,
    @SerializedName("collectedBy")
    val collectedBy: String,
    val remarks: String?,
    @SerializedName("createdBy")
    val createdBy: String
)

data class CollectSampleRequest(
    @SerializedName("visitID")
    val visitID: Int,
    @SerializedName("labCode")
    val labCode: String,
    @SerializedName("sampleBarcode")
    val sampleBarcode: String?,
    @SerializedName("collectedBy")
    val collectedBy: String
)

