package com.example.inventori2.features.register.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.features.register.data.datasources.models.RegisterRequest
import com.example.inventori2.features.register.domain.usecase.RegisterUseCase
import com.example.inventori2.features.register.presentation.screens.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _nombre = MutableStateFlow("")
    val nombre = _nombre.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    // Estado para visibilidad de contraseña
    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible = _passwordVisible.asStateFlow()

    // Preferencia de biometría
    private val _useBiometrics = MutableStateFlow(true)
    val useBiometrics = _useBiometrics.asStateFlow()

    fun onNombreChange(value: String) { _nombre.value = value }
    fun onEmailChange(value: String) { _email.value = value }
    fun onPasswordChange(value: String) { _password.value = value }
    
    fun onPasswordVisibilityChange() {
        _passwordVisible.value = !_passwordVisible.value
    }

    fun onBiometricsChange(enabled: Boolean) {
        _useBiometrics.value = enabled
    }

    fun register(nombre: String, email: String, pss: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val request = RegisterRequest(
                nombre = nombre,
                email = email,
                password = pss
            )
            
            val result = registerUseCase(request)
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { 
                        // Aquí se marcaría el éxito del registro
                        currentState.copy(isLoading = false, isRegistered = true) 
                    },
                    onFailure = { error -> currentState.copy(isLoading = false, error = error.message) }
                )
            }
        }
    }

    fun clearError() { _uiState.update { it.copy(error = null) } }
}
