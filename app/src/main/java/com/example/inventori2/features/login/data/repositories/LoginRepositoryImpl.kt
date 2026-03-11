package com.example.inventori2.features.login.data.repositories

import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.core.network.InventoriApi
import com.example.inventori2.features.login.data.datasources.mapper.toDomain
import com.example.inventori2.features.login.data.datasources.models.LoginRequest
import com.example.inventori2.features.login.domain.entities.User
import com.example.inventori2.features.login.domain.repositories.LoginRepository



class LoginRepositoryImpl(
    private val api: InventoriApi,
    private val tokenDataStore: TokenDataStore
) : LoginRepository {

    override suspend fun login(user: LoginRequest): User {
        val response = api.loginUser(user)

        // Guardar token
        tokenDataStore.saveToken(response.token)

        // Mapear user DTO -> Domain
        val userDomain = response.user.toDomain()


        // Guardar user en DataStore
        tokenDataStore.saveUser(userDomain)

        return userDomain
    }
}
