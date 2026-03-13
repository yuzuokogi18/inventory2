package com.example.inventori2.features.login.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.inventori2.features.login.data.datasources.models.LoginRequest

import com.example.inventori2.features.login.domain.entities.User
import com.example.inventori2.features.login.domain.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false,
    val user: User? = null
)

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    // ---------- UI STATE ----------
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState = _uiState.asStateFlow()

    // ---------- FORM STATE ----------
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible = _passwordVisible.asStateFlow()

    // ---------- EVENTS ----------
    fun onEmailChange(value: String) {
        _email.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun onPasswordVisibilityChange(visible: Boolean) {
        _passwordVisible.value = visible
    }

    // ---------- ACTION ----------
    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.update {
                it.copy(error = "Correo y contraseña son obligatorios")
            }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            // Especificamos el tipo explícitamente para que fold funcione
            val result: Result<User> = loginUseCase(
                LoginRequest(
                    email = email.trim(),
                    password = password
                )
            )

            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { user ->
                        currentState.copy(
                            isLoading = false,
                            user = user,
                            isLoggedIn = true,
                            error = null
                        )
                    },
                    onFailure = { error ->
                        currentState.copy(
                            isLoading = false,
                            error = error.message ?: "Error al iniciar sesión"
                        )
                    }
                )
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun logout() {
        _uiState.value = LoginUIState()
        _email.value = ""
        _password.value = ""
        _passwordVisible.value = false
    }
}
