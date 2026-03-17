package com.example.inventori2.features.product_detail.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventori2.core.ui.components.MainScaffold
import com.example.inventori2.features.product_detail.domain.entities.ProductDetail
import com.example.inventori2.features.product_detail.presentation.components.molecules.DetailRowMolecule
import com.example.inventori2.features.product_detail.presentation.components.molecules.StatusRowMolecule
import com.example.inventori2.features.product_detail.presentation.viewmodels.ProductDetailViewModel
import com.example.inventori2.features.product_create.presentation.components.organims.TopBarOrganism

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    MainScaffold(
        topAppBar = {
            TopBarOrganism(
                title = "Detalle del Producto",
                onBackClick = onBackClick
            )
        }
    ) {
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = uiState.error ?: "Error desconocido", color = Color.Red)
                }
            }
            uiState.product != null -> {
                ProductDetailContent(product = uiState.product!!, modifier = modifier)
            }
        }
    }
}

@Composable
fun ProductDetailContent(
    product: ProductDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        DetailRowMolecule(
            icon = Icons.Outlined.Inventory,
            label = "Nombre",
            value = product.nombre
        )

        Spacer(modifier = Modifier.height(20.dp))

        DetailRowMolecule(
            icon = Icons.Outlined.Category,
            label = "Categoría",
            value = product.categoriaId?.toString() ?: "No asignada"
        )

        Spacer(modifier = Modifier.height(20.dp))

        DetailRowMolecule(
            icon = Icons.Outlined.ConfirmationNumber,
            label = "Cantidad",
            value = "${product.cantidad} unidades"
        )

        Spacer(modifier = Modifier.height(20.dp))

        DetailRowMolecule(
            icon = Icons.Outlined.CalendarToday,
            label = "Vencimiento",
            value = formatDate(product.fechaVencimiento)
        )

        Spacer(modifier = Modifier.height(20.dp))

        StatusRowMolecule(isExpired = false) 
    }
}

private fun formatDate(dateString: String?): String {
    if (dateString.isNullOrBlank()) return "Sin fecha"
    return try {
        val cleanDate = dateString.split(" ").first().split("T").first()
        val parts = cleanDate.split("-")
        if (parts.size == 3) {
            "${parts[2]} de ${getMonthName(parts[1].toInt())} de ${parts[0]}"
        } else dateString
    } catch (e: Exception) {
        dateString
    }
}

private fun getMonthName(month: Int): String {
    return when (month) {
        1 -> "enero" 2 -> "febrero" 3 -> "marzo" 4 -> "abril" 5 -> "mayo" 6 -> "junio"
        7 -> "julio" 8 -> "agosto" 9 -> "septiembre" 10 -> "octubre" 11 -> "noviembre" 12 -> "diciembre"
        else -> ""
    }
}
