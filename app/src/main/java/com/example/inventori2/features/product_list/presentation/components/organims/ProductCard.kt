package com.example.inventori2.features.product_list.presentation.components.organims

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.inventori2.features.product_list.presentation.components.molecules.ProductActions
import com.example.inventori2.features.product_list.presentation.components.molecules.ProductHeader

@Composable

fun ProductCard(
    title: String,
    cantidad: Int,
    fechaVencimiento: String,
    onEditClick: () -> Unit,
    onViewClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProductHeader(
            title = title,
            cantidad = cantidad
        )

        HorizontalDivider(color = Color(0xFFE5E5E5))

        ProductActions(
            onEditClick = onEditClick,
            onViewClick = onViewClick,
            onDeleteClick = onDeleteClick
        )
    }
}