package com.example.inventori2.features.product_list.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventori2.features.product_delete.presentation.screens.ProductDeleteScreen
import com.example.inventori2.features.product_delete.presentation.viewmodels.ProductDeleteViewModel
import com.example.inventori2.features.product_delete.presentation.viewmodels.ProductDeleteViewModelFactory
import com.example.inventori2.features.product_list.presentation.components.organims.ProductsTopBar
import com.example.inventori2.features.product_list.presentation.viewmodels.ProductViewModel
import com.example.inventori2.features.product_list.presentation.viewmodels.ProductViewModelFactory
import com.example.inventori2.features.product_list.presentation.components.organims.ProductCard

@Composable
fun ProductsScreen(
    factory: ProductViewModelFactory,
    deleteFactory: ProductDeleteViewModelFactory,
    onEditClick: (Int) -> Unit = {},
    onViewClick: (Int) -> Unit = {},
    onCreateProductClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val viewModel: ProductViewModel = viewModel(factory = factory)
    val deleteViewModel: ProductDeleteViewModel = viewModel(factory = deleteFactory)
    
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val deleteError by deleteViewModel.error.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    // Estado para el Screen de eliminación
    var showDeleteScreen by remember { mutableStateOf(false) }
    var selectedProductId by remember { mutableIntStateOf(-1) }
    var selectedProductName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    LaunchedEffect(deleteError) {
        deleteError?.let {
            snackbarHostState.showSnackbar(it)
            deleteViewModel.clearError()
        }
    }

    // 🔴 Screen de borrado
    ProductDeleteScreen(
        show = showDeleteScreen,
        productId = selectedProductId,
        productName = selectedProductName,
        viewModel = deleteViewModel,
        onDismiss = { showDeleteScreen = false },
        onDeleteSuccess = {
            showDeleteScreen = false
            viewModel.loadProducts()
        }
    )

    Scaffold(
        topBar = { ProductsTopBar(title = "Mis productos") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateProductClick,
                containerColor = Color(0xFF6366F1)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null, tint = Color.White)
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.products.isEmpty() -> {
                    Text(
                        text = "No hay productos disponibles",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.products) { product ->
                            ProductCard(
                                title = product.nombre,
                                cantidad = product.cantidad,
                                fechaVencimiento = product.fechaVencimiento,
                                onEditClick = { onEditClick(product.id) },
                                onViewClick = { onViewClick(product.id) },
                                onDeleteClick = {
                                    selectedProductId = product.id
                                    selectedProductName = product.nombre
                                    showDeleteScreen = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}