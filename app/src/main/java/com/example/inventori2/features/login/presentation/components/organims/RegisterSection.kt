package com.example.inventori2.features.login.presentation.components.organims


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.inventori2.features.login.presentation.components.atoms.DividerWithText
import com.example.inventori2.features.login.presentation.components.atoms.SecondaryButton
import com.example.inventori2.features.login.presentation.components.atoms.VerticalSpacer

@Composable
fun RegisterSection(
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalSpacer(24)
        DividerWithText(text = "o")
        VerticalSpacer(24)

        SecondaryButton(
            text = "Registrarse",
            onClick = onRegisterClick,
            enabled = enabled
        )
    }
}
