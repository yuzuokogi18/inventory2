package com.example.inventori2.features.inventory.product_delete.presentation.components.molecules

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DeleteConfirmationDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    productName: String
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Eliminar Producto") },
            text = { Text(text = "¿Estás seguro de que deseas eliminar el producto \"$productName\"? Esta acción no se puede deshacer.") },
            confirmButton = {
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Eliminar", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        )
    }
}