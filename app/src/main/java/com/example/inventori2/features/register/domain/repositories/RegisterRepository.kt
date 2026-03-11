package com.example.inventori2.features.register.domain.repositories

import com.example.inventori2.features.register.domain.entities.User
import com.example.inventori2.features.register.data.datasources.models.RegisterRequest

interface RegisterRepository {

    suspend fun registerUser(user: RegisterRequest): User
}
