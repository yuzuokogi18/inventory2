package com.example.inventori2.features.product_delete.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventori2.features.product_delete.presentation.components.molecules.DeleteConfirmationDialog
import com.example.inventori2.features.product_delete.presentation.viewmodels.ProductDeleteViewModel

@Composable
fun ProductDeleteScreen(
    show: Boolean,
    productId: Int,
    productName: String,
    viewModel: ProductDeleteViewModel,
    onDismiss: () -> Unit,
    onDeleteSuccess: () -> Unit
) {
    val isDeleted by viewModel.isDeleted.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()


    LaunchedEffect(isDeleted) {
        if (isDeleted) {
            onDeleteSuccess()
            viewModel.resetDeleteStatus()
        }
    }

    DeleteConfirmationDialog(
        show = show,
        productName = productName,
        onDismiss = onDismiss,
        onConfirm = {
            viewModel.deleteProduct(productId)
        }
    )
}