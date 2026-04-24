package com.example.inventori2.features.register.presentation.components.organims

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventori2.features.register.presentation.components.molecules.InputField
import com.example.inventori2.features.register.presentation.components.molecules.PasswordInputField

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
    var confirmPassword by remember { mutableStateOf("") }
    var useBiometrics by remember { mutableStateOf(true) }
    
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
        Text(
            text = "Crear tu cuenta",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Comienza a gestionar tu despensa de forma inteligente.",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        // CAMPO: Nombre Completo
        InputField(
            label = "Nombre completo",
            value = nombre,
            onValueChange = onNombreChange,
            placeholder = "Ej. Juan Pérez",
            enabled = !isLoading,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // CAMPO: Correo Electrónico
        InputField(
            label = "Correo electrónico",
            value = email,
            onValueChange = onEmailChange,
            placeholder = "ejemplo@correo.com",
            enabled = !isLoading,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // CAMPO: Contraseña
        PasswordInputField(
            label = "Contraseña",
            value = password,
            onValueChange = onPasswordChange,
            placeholder = "Mínimo 8 caracteres",
            passwordVisible = passwordVisible,
            onVisibilityChange = { onPasswordVisibilityChange(!passwordVisible) },
            enabled = !isLoading
        )
        
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Fortaleza: Media", fontSize = 10.sp, color = Color.Gray)
            Text("Usa números y símbolos", fontSize = 10.sp, color = Color.Gray)
        }

        // CAMPO: Confirmar Contraseña
        PasswordInputField(
            label = "Confirmar contraseña",
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = "Repite tu contraseña",
            passwordVisible = passwordVisible,
            onVisibilityChange = { onPasswordVisibilityChange(!passwordVisible) },
            enabled = !isLoading
        )
        if (confirmPassword.isNotEmpty() && confirmPassword != password) {
            Text("⚠ Las contraseñas no coinciden", color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SECCIÓN: Acceso por huella (Hardware)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFF8F9FA),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(40.dp).background(Color(0xFFDCFCE7), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Fingerprint, contentDescription = null, tint = Color(0xFF22C55E))
                }
                Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                    Text("Acceso por huella", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Más rápido y seguro", fontSize = 12.sp, color = Color.Gray)
                }
                Switch(
                    checked = useBiometrics,
                    onCheckedChange = { useBiometrics = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF22C55E))
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // BOTÓN: Crear cuenta
        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E)),
            enabled = !isLoading && nombre.isNotBlank() && email.isNotBlank() && password == confirmPassword
        ) {
            Text("Crear cuenta", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        // ENLACE: Acceder
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("¿Ya tienes cuenta? ", color = Color.Gray)
            Text(
                "Acceder",
                color = Color(0xFF22C55E),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            "Al registrarte, aceptas nuestros Términos de Servicio y la Política de Privacidad de PantryGuard+.",
            textAlign = TextAlign.Center,
            fontSize = 10.sp,
            color = Color.LightGray,
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF22C55E))
        }
    }

    SnackbarHost(hostState = snackbarHostState)
}
