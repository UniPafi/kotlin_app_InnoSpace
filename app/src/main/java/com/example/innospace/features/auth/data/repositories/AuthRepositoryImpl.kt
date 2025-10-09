package com.example.innospace.features.auth.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.innospace.features.auth.data.models.LoginRequestDto
import com.example.innospace.features.auth.data.models.RegisterRequestDto
import com.example.innospace.features.auth.data.remote.AuthService
import com.example.innospace.features.auth.domain.models.User
import com.example.innospace.features.auth.domain.repositories.AuthRepository

class AuthRepositoryImpl(private val service: AuthService) : AuthRepository {

    override suspend fun login(email: String, password: String): User? =
        withContext(Dispatchers.IO) {
            try {
                val response = service.login(LoginRequestDto(email, password))
                if (response.isSuccessful) {
                    response.body()?.let { loginResponseDto ->
                        return@withContext User(
                            id = loginResponseDto.id,
                            email = loginResponseDto.email,
                            token = loginResponseDto.token
                        )
                    }
                }
                return@withContext null
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }

    override suspend fun register(name: String, email: String, password: String, accountType: String): User? =
        withContext(Dispatchers.IO) {
            try {
                val response = service.register(RegisterRequestDto(name, email, password, accountType))
                if (response.isSuccessful) {
                    response.body()?.let { userResource ->
                        return@withContext User(
                            id = userResource.id,
                            email = userResource.email,
                            accountType = userResource.accountType
                        )
                    }
                }
                return@withContext null
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
}