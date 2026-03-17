package com.example.inventori2.features.login.presentation.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.core.hardware.domain.BiometriaManager
import com.example.inventori2.features.login.data.datasources.models.LoginRequest
import com.example.inventori2.features.login.domain.usecases.LoginUseCase
import com.example.inventori2.features.login.presentation.screens.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val biometriaManager: BiometriaManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible = _passwordVisible.asStateFlow()

    fun onEmailChange(value: String) { _email.value = value }
    fun onPasswordChange(value: String) { _password.value = value }
    fun onPasswordVisibilityChange() { _passwordVisible.value = !_passwordVisible.value }

    fun login(email: String, pss: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val request = LoginRequest(email = email, password = pss)
            val result = loginUseCase(request)
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { currentState.copy(isLoading = false, isLoggedIn = true) },
                    onFailure = { error -> currentState.copy(isLoading = false, error = error.message) }
                )
            }
        }
    }

    fun loginConBiometria(activity: FragmentActivity) {
        if (biometriaManager.estaDisponible()) {
            biometriaManager.autenticar(
                activity = activity,
                titulo = "Inicio de Sesión",
                subtitulo = "Usa tu huella para entrar al inventario",
                onSuccess = {
                    _uiState.update { it.copy(isLoggedIn = true) }
                },
                onError = { _, error ->
                    _uiState.update { it.copy(error = error.toString()) }
                }
            )
        } else {
            _uiState.update { it.copy(error = "Biometría no disponible en este dispositivo") }
        }
    }

    fun clearError() { _uiState.update { it.copy(error = null) } }
}
