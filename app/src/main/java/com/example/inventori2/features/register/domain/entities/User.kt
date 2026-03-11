package com.example.inventori2.features.register.domain.entities

data class User(
    val id: Int,
    val nombre: String,
    val email: String,
    val createdAt: String? = null
)