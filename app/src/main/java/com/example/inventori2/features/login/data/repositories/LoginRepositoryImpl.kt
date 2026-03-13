package com.example.inventori2.features.login.data.repositories

import com.example.inventori2.core.database.dao.UserDao
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.features.login.data.datasources.models.LoginRequest
import com.example.inventori2.features.login.domain.entities.User
import com.example.inventori2.features.login.domain.repositories.LoginRepository

// Quitamos @Inject porque ya se provee en AppContainer.kt
class LoginRepositoryImpl(
    private val userDao: UserDao,
    private val tokenDataStore: TokenDataStore
) : LoginRepository {

    override suspend fun login(user: LoginRequest): User {
        val userEntity = userDao.login(user.email, user.password)
            ?: throw Exception("Credenciales incorrectas")

        val userDomain = User(
            id = userEntity.id,
            nombre = userEntity.nombre,
            email = userEntity.email
        )

        tokenDataStore.saveToken("local_jwt_token_${userEntity.id}")
        tokenDataStore.saveUser(userDomain)

        return userDomain
    }
}