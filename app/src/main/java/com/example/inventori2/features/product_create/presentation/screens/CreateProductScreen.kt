package com.example.inventori2.features.product_create.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventori2.core.ui.components.MainScaffold
import com.example.inventori2.features.product_create.presentation.components.organims.ProductFormOrganism
import com.example.inventori2.features.product_create.presentation.viewmodels.CreateProductViewModel
import com.example.inventori2.features.product_create.presentation.components.organims.TopBarOrganism

@Composable
fun CreateProductScreen(
    onBackClick: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: CreateProductViewModel = hiltViewModel() // Inyectado por Hilt
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val nombre by viewModel.nombre.collectAsStateWithLifecycle()
    val cantidad by viewModel.cantidad.collectAsStateWithLifecycle()
    val fechaVencimiento by viewModel.fechaVencimiento.collectAsStateWithLifecycle()
    val categoriaId by viewModel.categoriaId.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onSuccess()
        }
    }

    MainScaffold(
        topAppBar = {
            TopBarOrganism(
                title = "Crear producto",
                onBackClick = onBackClick
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        ProductFormOrganism(
            nombre = nombre,
            onNombreChange = viewModel::onNombreChange,
            cantidad = cantidad,
            onCantidadChange = viewModel::onCantidadChange,
            fechaVencimiento = fechaVencimiento,
            onFechaVencimientoChange = viewModel::onFechaVencimientoChange,
            categoriaId = categoriaId,
            onCategoriaSelected = viewModel::onCategoriaChange,
            onCancel = onBackClick,
            onSave = { viewModel.createProduct() },
            isLoading = uiState.isLoading,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}
