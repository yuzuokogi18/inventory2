package com.example.inventori2.features.login.presentation.screens

import com.example.inventori2.features.login.domain.entities.User

/**
 * Estado de UI para autenticación/login
 * Usado en ViewModel con StateFlow
 */
data class LoginUiState( // Corregido: LoginUIState -> LoginUiState
    val isLoading: Boolean = false,
    val user: User? = null,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)
