package com.example.inventori2.features.register.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventori2.core.ui.components.MainScaffold
import com.example.inventori2.features.register.presentation.components.organims.RegistrationForm
import com.example.inventori2.features.register.presentation.viewmodels.RegisterViewModel

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {},
    viewModel: RegisterViewModel = hiltViewModel()
) {
    // Corregido: RegisterUIState -> RegisterUiState y simplificado collectAsStateWithLifecycle
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val nombre by viewModel.nombre.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered) {
            onRegisterSuccess()
        }
    }

    MainScaffold {
        RegistrationForm(
            nombre = nombre,
            onNombreChange = viewModel::onNombreChange,
            email = email,
            onEmailChange = viewModel::onEmailChange,
            password = password,
            onPasswordChange = viewModel::onPasswordChange,
            passwordVisible = false,
            onPasswordVisibilityChange = { },
            onRegisterClick = { viewModel.register(nombre, email, password) },
            onLoginClick = onNavigateToLogin,
            isLoading = uiState.isLoading,
            error = uiState.error,
            onClearError = viewModel::clearError
        )
    }
}
