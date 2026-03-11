package com.example.inventori2.features.login.data.datasources.mapper
import com.example.inventori2.features.login.data.datasources.models.LoginDTO
import com.example.inventori2.features.login.domain.entities.User

fun LoginDTO.toDomain(): User {
    return User(
        id = this.id,
        nombre = this.nombre,
        email = this.email,

    )
}
