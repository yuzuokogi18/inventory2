package com.example.inventori2.features.inventory.product_detail.presentation.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.inventori2.core.ui.components.MainScaffold
import com.example.inventori2.features.inventory.product_create.presentation.components.organims.TopBarOrganism
import com.example.inventori2.features.inventory.product_detail.domain.entities.ProductDetail
import com.example.inventori2.features.inventory.product_detail.presentation.components.molecules.DetailRowMolecule
import com.example.inventori2.features.inventory.product_detail.presentation.components.molecules.StatusRowMolecule
import com.example.inventori2.features.inventory.product_detail.presentation.viewmodels.ProductDetailViewModel

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
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize().background(Color(0xFFF9FAFB))) {
            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF22C55E))
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
}

@Composable
fun ProductDetailContent(
    product: ProductDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // IMAGEN DEL PRODUCTO
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            if (product.imagenUri != null) {
                AsyncImage(
                    model = Uri.parse(product.imagenUri),
                    contentDescription = product.nombre,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Image, 
                        contentDescription = null, 
                        modifier = Modifier.size(60.dp), 
                        tint = Color.LightGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // INFORMACIÓN DETALLADA
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                DetailRowMolecule(
                    icon = Icons.Outlined.Inventory,
                    label = "Nombre",
                    value = product.nombre
                )

                Spacer(modifier = Modifier.height(20.dp))

                DetailRowMolecule(
                    icon = Icons.Outlined.Category,
                    label = "Categoría",
                    value = when(product.categoriaId) {
                        1 -> "Bebidas" 2 -> "Lácteos" 3 -> "Carnes" 4 -> "Frutas" 5 -> "Snacks" else -> "General"
                    }
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

                Spacer(modifier = Modifier.height(24.dp))

                StatusRowMolecule(isExpired = false) 
            }
        }
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
