package com.example.innospace.features.auth.domain.repositories

import com.example.innospace.features.auth.domain.models.User

interface AuthRepository {
    suspend fun login(email: String, password: String): User?
    suspend fun register(name: String, email: String, password: String, accountType: String): User?
}