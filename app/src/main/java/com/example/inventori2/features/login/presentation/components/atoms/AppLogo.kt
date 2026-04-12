package com.example.inventori2.features.login.presentation.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.inventori2.ui.theme.PrimaryGreen

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    size: Int = 100
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .background(Color.White, CircleShape)
            .border(1.dp, Color(0xFFEEEEEE), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Shield,
            contentDescription = null,
            tint = PrimaryGreen,
            modifier = Modifier.size((size * 0.6).dp)
        )
    }
}