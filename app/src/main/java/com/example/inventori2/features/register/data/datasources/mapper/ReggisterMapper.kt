package com.example.inventori2.features.register.data.datasources.mapper

import com.example.inventori2.features.register.data.datasources.models.RegisterDTO
import com.example.inventori2.features.register.domain.entities.User

fun RegisterDTO.toDomain(): User {
    return User(
        id = id,
        nombre = nombre,
        email = email,
        createdAt = createdAt,
    )
}
