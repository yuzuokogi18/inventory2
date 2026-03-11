package com.example.inventori2.features.login.presentation.components.organims

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.inventori2.features.login.presentation.components.atoms.PrimaryButton
import com.example.inventori2.features.login.presentation.components.atoms.VerticalSpacer

@Composable
fun LoginActions(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Column(modifier = modifier.fillMaxWidth()) {

        VerticalSpacer(24)

        PrimaryButton(
            text = if (isLoading) "Iniciando..." else "Iniciar Sesión",
            onClick = onLoginClick,
            enabled = enabled,
            isLoading = isLoading
        )

        VerticalSpacer(16)
    }
}

