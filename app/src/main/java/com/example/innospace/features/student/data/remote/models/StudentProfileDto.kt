package com.example.innospace.features.student.data.remote.models

import com.google.gson.annotations.SerializedName

data class StudentProfileDto(
    @SerializedName("id") val id: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("photoUrl") val photoUrl: String?,
    @SerializedName("skills") val skills: List<String>,
    @SerializedName("experiences") val experiences: List<String>
)