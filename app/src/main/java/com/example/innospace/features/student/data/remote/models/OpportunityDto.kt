package com.example.innospace.features.student.data.remote.models

import com.google.gson.annotations.SerializedName

data class OpportunityDto(
    @SerializedName("id") val id: Long,
    @SerializedName("companyId") val companyId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("requirements") val requirements: List<String>,
    @SerializedName("status") val status: String
)