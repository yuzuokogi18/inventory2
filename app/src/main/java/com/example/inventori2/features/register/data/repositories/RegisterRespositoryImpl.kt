package com.example.inventori2.features.register.data.repositories

import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.core.network.InventoriApi
import com.example.inventori2.features.register.data.datasources.mapper.toDomain
import com.example.inventori2.features.register.data.datasources.models.RegisterRequest
import com.example.inventori2.features.register.domain.entities.User
import com.example.inventori2.features.register.domain.repositories.RegisterRepository

class RegisterRepositoryImpl(
    private val api: InventoriApi,
    private val tokenDataStore: TokenDataStore
) : RegisterRepository {

    override suspend fun registerUser(user: RegisterRequest): User {
        val response = api.registerUser(user)

        if (response.token.isNotEmpty()) {
            tokenDataStore.saveToken(response.token)
        }

        return response.user.toDomain()
    }
}
