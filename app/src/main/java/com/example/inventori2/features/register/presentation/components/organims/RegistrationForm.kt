package com.example.inventori2.features.register.presentation.components.organims



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventori2.features.register.presentation.components.atoms.PrimaryButton
import com.example.inventori2.features.register.presentation.components.molecules.InputField
import com.example.inventori2.features.register.presentation.components.molecules.LoginPrompt
import com.example.inventori2.features.register.presentation.components.molecules.InputField
import com.example.inventori2.features.register.presentation.components.molecules.TermsAndPrivacy

@Composable
fun RegistrationForm(
    modifier: Modifier = Modifier,
    nombre: String,
    onNombreChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
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
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            InputField(
                label = "Nombre completo",
                value = nombre,
                onValueChange = onNombreChange,
                placeholder = "Introduce tu nombre completo",
                enabled = !isLoading
            )

            InputField(
                label = "Correo electrónico",
                value = email,
                onValueChange = onEmailChange,
                placeholder = "Introduce tu correo electrónico",
                enabled = !isLoading
            )

            InputField(
                label = "Contraseña",
                value = password,
                onValueChange = onPasswordChange,
                placeholder = "Crea una contraseña",
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = if (isLoading) "Registrando..." else "Registrarse",
                onClick = onRegisterClick,
                enabled = !isLoading && nombre.isNotBlank() &&
                        email.isNotBlank() && password.isNotBlank()
            )

            LoginPrompt(
                onLoginClick = onLoginClick
            )

            TermsAndPrivacy()

            Spacer(modifier = Modifier.height(24.dp))
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
