package com.example.inventori2.features.login.presentation.components.organims



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.inventori2.features.login.presentation.components.atoms.AppLogo
import com.example.inventori2.features.login.presentation.components.atoms.VerticalSpacer

@Composable
fun AuthHeader(
    modifier: Modifier = Modifier,
    logoSize: Int = 80
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalSpacer(80)
        AppLogo(size = logoSize)
        VerticalSpacer(48)
    }
}
