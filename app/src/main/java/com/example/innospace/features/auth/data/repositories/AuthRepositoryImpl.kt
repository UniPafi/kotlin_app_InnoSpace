package com.example.innospace.features.auth.data.repositories

import com.example.innospace.core.networking.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.innospace.features.auth.data.remote.models.LoginRequestDto
import com.example.innospace.features.auth.data.remote.models.RegisterRequestDto
import com.example.innospace.features.auth.data.remote.AuthService
import com.example.innospace.features.auth.domain.models.User
import com.example.innospace.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: AuthService,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): User? = withContext(Dispatchers.IO) {
        try {
            val response = service.login(com.example.innospace.features.auth.data.remote.models.LoginRequestDto(email, password))
            if (response.isSuccessful) {
                response.body()?.let { dto ->

                    dto.token?.let { token ->
                        sessionManager.saveUserSession(
                            userId = dto.id,
                            token = token
                        )
                    }

                    return@withContext User(
                        id = dto.id,
                        email = dto.email,
                        token = dto.token
                    )
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun register(name: String, email: String, password: String, accountType: String): User? =
        withContext(Dispatchers.IO) {
            try {
                val response = service.register(
                    com.example.innospace.features.auth.data.remote.models.RegisterRequestDto(
                        name, email, password, accountType
                    )
                )
                if (response.isSuccessful) {
                    response.body()?.let { dto ->
                        return@withContext User(
                            id = dto.id,
                            email = dto.email,
                            accountType = dto.accountType
                        )
                    }
                }
                null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
}