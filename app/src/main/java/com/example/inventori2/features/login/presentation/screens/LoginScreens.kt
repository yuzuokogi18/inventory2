package com.example.inventori2.features.login.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventori2.core.ui.components.MainScaffold
import com.example.inventori2.features.login.presentation.components.organims.LoginContent
import com.example.inventori2.features.login.presentation.components.organisms.LoginHeader
import com.example.inventori2.features.login.presentation.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(LoginUiState())
    val email by viewModel.email.collectAsStateWithLifecycle("")
    val password by viewModel.password.collectAsStateWithLifecycle("")
    val visiblePassword by viewModel.passwordVisible.collectAsStateWithLifecycle(false)
    
    val activity = LocalContext.current as? FragmentActivity

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
            onChangeVisible = { viewModel.onPasswordVisibilityChange() },
            onLoginClick = { viewModel.login(email, password) },
            onBiometricClick = { activity?.let { viewModel.loginConBiometria(it) } },
            onRegisterClick = onNavigateToRegister,
            isLoading = uiState.isLoading,
            error = uiState.error,
            onClearError = viewModel::clearError
        )
    }
}
