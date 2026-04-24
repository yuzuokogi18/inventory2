package com.example.inventori2.features.inventory.product_list.presentation.components.molecules


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.inventori2.features.inventory.product_list.presentation.components.atoms.StatusBadge

@Composable
fun ProductHeader(
    title: String,
    cantidad: Int,
    modifier: Modifier = Modifier
) {
    val isInStock = cantidad > 0

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        StatusBadge(
            text = if (isInStock) "En stock" else "Agotado",
            isActive = isInStock
        )
    }
}