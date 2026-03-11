package com.example.inventori2.features.register.presentation.screens

import com.example.inventori2.features.register.domain.entities.User

data class RegisterUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: User? = null,
    val isRegistered: Boolean = false
)
