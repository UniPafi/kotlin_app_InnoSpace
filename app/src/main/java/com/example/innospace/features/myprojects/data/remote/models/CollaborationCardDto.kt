package com.example.innospace.features.myprojects.data.remote.models

import com.google.gson.annotations.SerializedName

data class CollaborationCardDto(
    @SerializedName("collaborationId") val collaborationId: Long,
    @SerializedName("projectId") val projectId: Long,
    @SerializedName("projectTitle") val projectTitle: String,
    @SerializedName("projectDescription") val projectDescription: String,
    @SerializedName("managerId") val managerId: Long,
    @SerializedName("managerName") val managerName: String,
    @SerializedName("companyName") val companyName: String,
    @SerializedName("managerPhotoUrl") val managerPhotoUrl: String,
    @SerializedName("studentResponse") val studentResponse: String
)