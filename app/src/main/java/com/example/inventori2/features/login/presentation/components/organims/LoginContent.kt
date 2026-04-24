package com.example.inventori2.features.login.presentation.components.organims

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventori2.features.register.presentation.components.molecules.PasswordInputField
import com.example.inventori2.features.register.presentation.components.molecules.InputField

@Composable
fun LoginContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onBiometricClick: () -> Unit,
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Quitamos el Text manual ya que el InputField ya tiene Label incluido internamente
        InputField(
            label = "Correo Electronico",
            value = email,
            onValueChange = onEmailChange,
            placeholder = "Ejemplo@gmail.com",
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordInputField(
            label = "Contraseña",
            value = password,
            onValueChange = onPasswordChange,
            placeholder = "Ingrese tu contraseña",
            passwordVisible = passwordVisible,
            onVisibilityChange = onChangeVisible,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(60.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onLoginClick,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFF22C55E)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF22C55E)
                ),
                enabled = !isLoading && email.isNotBlank() && password.isNotBlank()
            ) {
                Text(
                    text = if (isLoading) "Iniciando..." else "Inicie sesion",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(Color(0xFFF0FDF4), CircleShape)
                    .border(1.dp, Color(0xFFDCFCE7), CircleShape)
                    .clickable(enabled = !isLoading) { onBiometricClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = "Login con huella",
                    tint = Color(0xFF22C55E),
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(
            onClick = onRegisterClick,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = !isLoading
        ) {
            Text(
                text = "¿No tines cuenta? Registratie",
                color = Color(0xFF64748B),
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Tu información biométrica está cifrada y nunca sale de este dispositivo.",
            textAlign = TextAlign.Center,
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF22C55E))
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(16.dp)
    )
}
