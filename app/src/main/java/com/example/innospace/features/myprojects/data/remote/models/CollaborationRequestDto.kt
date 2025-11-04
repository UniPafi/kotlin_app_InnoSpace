package com.example.innospace.features.myprojects.data.remote.models

import com.google.gson.annotations.SerializedName

data class CollaborationRequestDto(
    @SerializedName("id") val id: Long,
    @SerializedName("projectId") val projectId: Long,
    @SerializedName("managerId") val managerId: Long,
    @SerializedName("status") val status: String
)