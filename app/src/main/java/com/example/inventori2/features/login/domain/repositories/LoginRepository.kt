package com.example.inventori2.features.login.domain.repositories
import com.example.inventori2.features.login.data.datasources.models.LoginRequest
import com.example.inventori2.features.login.domain.entities.User

interface LoginRepository {
    suspend fun login(user: LoginRequest): User
}