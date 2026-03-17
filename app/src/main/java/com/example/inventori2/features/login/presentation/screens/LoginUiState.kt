package com.example.inventori2.features.login.presentation.screens

import com.example.inventori2.features.login.domain.entities.User


data class LoginUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)
