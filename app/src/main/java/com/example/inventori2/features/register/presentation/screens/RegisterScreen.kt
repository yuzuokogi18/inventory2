package com.example.inventori2.features.register.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventori2.features.register.presentation.components.molecules.TopBar
import com.example.inventori2.features.register.presentation.components.organims.RegistrationForm
import com.example.inventori2.features.register.presentation.viewmodels.RegisterViewModel
import com.example.inventori2.features.register.presentation.viewmodels.RegisterViewModelFactory


@Composable
fun RegisterScreen(
    factory: RegisterViewModelFactory,
    onNavigateToLogin: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {}
) {
    val viewModel: RegisterViewModel = viewModel(factory = factory)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val nombre by viewModel.nombre.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val passwordVisible by viewModel.passwordVisible.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered) {
            onRegisterSuccess()
        }
    }

    Scaffold(
        topBar = { TopBar("Crear cuenta", onNavigateToLogin) }
    ) { paddingValues ->
        RegistrationForm(
            modifier = Modifier.padding(paddingValues),
            nombre = nombre,
            onNombreChange = viewModel::onNombreChange,
            email = email,
            onEmailChange = viewModel::onEmailChange,
            password = password,
            onPasswordChange = viewModel::onPasswordChange,
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = viewModel::onPasswordVisibilityChange,
            onRegisterClick = { viewModel.register() },
            onLoginClick = onNavigateToLogin,
            isLoading = uiState.isLoading,
            error = uiState.error,
            onClearError = viewModel::clearError
        )
    }
}
