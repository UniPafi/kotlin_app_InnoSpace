package com.example.innospace.features.auth.data.remote

import com.example.innospace.features.auth.data.models.LoginRequestDto
import com.example.innospace.features.auth.data.models.LoginResponseDto
import com.example.innospace.features.auth.data.models.RegisterRequestDto
import com.example.innospace.features.auth.data.models.UserResource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {
    @Headers("Content-Type: application/json")
    @POST("api/v1/authentication/sign-in")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): Response<LoginResponseDto>

    @Headers("Content-Type: application/json")
    @POST("api/v1/authentication/sign-up")
    suspend fun register(@Body registerRequestDto: RegisterRequestDto): Response<UserResource>
}