package com.example.inventori2.features.inventory.product_detail.presentation.components.atoms



import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun DetailValueAtom(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = Color.Black,
        modifier = modifier
    )
}