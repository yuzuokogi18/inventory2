package com.example.inventori2.features.login.presentation.components.organims

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.inventori2.features.login.presentation.components.atoms.PrimaryButton
import com.example.inventori2.features.register.presentation.components.molecules.PasswordInputField
import com.example.inventori2.features.register.presentation.components.molecules.InputField

@Composable
fun LoginContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onBiometricClick: () -> Unit, // NUEVO
    onChangeVisible: (Boolean) -> Unit,
    passwordVisible: Boolean,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    error: String? = null,
    onClearError: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(it)
            onClearError()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            InputField(
                label = "Correo electrónico",
                value = email,
                onValueChange = onEmailChange,
                placeholder = "ejemplo@correo.com",
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordInputField(
                label = "Contraseña",
                value = password,
                onValueChange = onPasswordChange,
                placeholder = "Ingresa tu contraseña",
                passwordVisible = passwordVisible,
                onVisibilityChange = onChangeVisible,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                PrimaryButton(
                    text = if (isLoading) "Iniciando sesión..." else "Iniciar sesión",
                    onClick = onLoginClick,
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading && email.isNotBlank() && password.isNotBlank()
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // BOTÓN DE HARDWARE: Biometría
                IconButton(
                    onClick = onBiometricClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Login con huella",
                        tint = Color(0xFF6366F1),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = onRegisterClick,
                enabled = !isLoading
            ) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        )
    }
}
