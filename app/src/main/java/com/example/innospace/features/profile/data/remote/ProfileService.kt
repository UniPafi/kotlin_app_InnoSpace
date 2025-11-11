package com.example.innospace.features.profile.data.remote

import com.example.innospace.features.profile.data.remote.models.StudentProfileDto
import com.example.innospace.features.profile.data.remote.models.UpdateProfileDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileService {

    @GET("api/v1/student-profiles")
    suspend fun getAllStudentProfiles(): Response<List<StudentProfileDto>>

    @GET("api/v1/student-profiles/{profileId}")
    suspend fun getStudentProfileById(@Path("profileId") profileId: Long): Response<StudentProfileDto>

    @PUT("api/v1/student-profiles/{id}")
    suspend fun updateStudentProfile(
        @Path("id") id: Long,
        @Body updateProfileDto: UpdateProfileDto
    ): Response<StudentProfileDto>
}

