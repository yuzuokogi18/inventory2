package com.example.inventori2.features.login.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventori2.core.theme.ui.EduTrackTheme
import com.example.inventori2.core.ui.components.MainScaffold
import com.example.inventori2.features.login.presentation.components.organims.LoginContent
import com.example.inventori2.features.login.presentation.components.organisms.LoginHeader
import com.example.inventori2.features.login.presentation.viewmodels.LoginViewModel
import com.example.inventori2.features.login.presentation.viewmodels.LoginViewModelFactory


@Composable
fun LoginScreen(
    factory: LoginViewModelFactory,
    onNavigateToRegister: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    val viewModel: LoginViewModel = viewModel(factory = factory)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val visiblePassword by viewModel.passwordVisible.collectAsStateWithLifecycle()

    // Navegar cuando el login es exitoso
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            onLoginSuccess()
        }
    }

    MainScaffold {
        LoginHeader()
        LoginContent(
            email = email,
            onEmailChange = viewModel::onEmailChange,
            password = password,
            onPasswordChange = viewModel::onPasswordChange,
            passwordVisible = visiblePassword,
            onChangeVisible = viewModel::onPasswordVisibilityChange,
            onLoginClick = { viewModel.login(email, password) },
            onRegisterClick = onNavigateToRegister,
            isLoading = uiState.isLoading,
            error = uiState.error,
            onClearError = viewModel::clearError
        )
    }
}


