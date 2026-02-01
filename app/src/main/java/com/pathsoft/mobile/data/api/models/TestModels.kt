package com.pathsoft.mobile.data.api.models

import com.google.gson.annotations.SerializedName

data class TestResponse(
    @SerializedName("sid")
    val sid: Int,
    @SerializedName("sname")
    val sname: String,
    val rate: Double,
    val tat: String?,
    @SerializedName("sampleProcessingAt")
    val sampleProcessingAt: String?,
    @SerializedName("sampleTypeId")
    val sampleTypeId: Int?,
    @SerializedName("sampleTypeName")
    val sampleTypeName: String?
)

data class PackageResponse(
    @SerializedName("pid")
    val pid: Int,
    @SerializedName("packageName")
    val packageName: String,
    val description: String?,
    @SerializedName("Amount")
    val Amount: Double
)

data class PackageTestsResponse(
    val data: List<TestResponse>
)

data class TestParametersResponse(
    @SerializedName("sidList")
    val sidList: String,
    val tests: List<TestWithParameters>
)

data class TestWithParameters(
    @SerializedName("sid")
    val sid: Int,
    @SerializedName("testName")
    val testName: String,
    val parameters: List<TestParameter>,
    val sections: List<Section>?
)

data class TestParameter(
    @SerializedName("parameterID")
    val parameterID: Int,
    @SerializedName("parameterName")
    val parameterName: String,
    @SerializedName("normalRangeMin")
    val normalRangeMin: Double?,
    @SerializedName("normalRangeMax")
    val normalRangeMax: Double?,
    val unit: String?,
    @SerializedName("parameterType")
    val parameterType: String?
)

data class Section(
    @SerializedName("sectionID")
    val sectionID: Int,
    @SerializedName("sectionName")
    val sectionName: String,
    val parameters: List<TestParameter>
)

