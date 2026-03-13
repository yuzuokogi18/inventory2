package com.example.inventori2.features.register.data.repositories

import com.example.inventori2.core.database.dao.UserDao
import com.example.inventori2.core.database.entities.UserEntity
import com.example.inventori2.features.register.data.datasources.models.RegisterRequest
import com.example.inventori2.features.register.domain.entities.User
import com.example.inventori2.features.register.domain.repositories.RegisterRepository

// Quitamos @Inject porque ya se provee en AppContainer.kt
class RegisterRepositoryImpl(
    private val userDao: UserDao
) : RegisterRepository {

    override suspend fun registerUser(user: RegisterRequest): User {
        val existingUser = userDao.getUserByEmail(user.email)
        if (existingUser != null) {
            throw Exception("El correo electrónico ya está registrado")
        }

        val userEntity = UserEntity(
            nombre = user.nombre,
            email = user.email,
            contrasena = user.password
        )

        val id = userDao.register(userEntity)

        return User(
            id = id.toInt(),
            nombre = user.nombre,
            email = user.email
        )
    }
}