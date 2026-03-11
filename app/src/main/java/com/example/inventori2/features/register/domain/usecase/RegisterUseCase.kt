package com.example.inventori2.features.register.domain.usecase


import com.example.inventori2.features.register.domain.entities.User
import com.example.inventori2.features.register.data.datasources.models.RegisterRequest
import com.example.inventori2.features.register.domain.repositories.RegisterRepository

class RegisterUseCase(
    private val repository: RegisterRepository
) {

    suspend operator fun invoke(
        registerRequest: RegisterRequest
    ): Result<User> {
        return try {
            val user = repository.registerUser(registerRequest)

            if (user.email.isBlank()) {
                Result.failure(Exception("Error al registrar usuario"))
            } else {
                Result.success(user)
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
