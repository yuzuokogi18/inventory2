package com.example.inventori2.features.notification.domain.repositories

interface FcmRepository {
    suspend fun updateToken(token: String): Result<Unit>
}
