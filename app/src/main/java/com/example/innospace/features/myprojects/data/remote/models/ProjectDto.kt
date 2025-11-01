package com.example.innospace.features.myprojects.data.remote.models

import com.google.gson.annotations.SerializedName

data class ProjectDto(
    @SerializedName("id") val id: Long,
    @SerializedName("studentId") val studentId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("summary") val summary: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("status") val status: String
)