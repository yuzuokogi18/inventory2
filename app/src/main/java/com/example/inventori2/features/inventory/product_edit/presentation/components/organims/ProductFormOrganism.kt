package com.example.inventori2.features.inventory.product_edit.presentation.components.organims

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventori2.features.inventory.product_edit.presentation.components.molecules.ActionButtonsMolecule
import com.example.inventori2.features.inventory.product_edit.presentation.components.molecules.FormFieldMolecule

@Composable
fun ProductFormOrganism(
    nombre: String,
    onNombreChange: (String) -> Unit,
    cantidad: String,
    onCantidadChange: (String) -> Unit,
    fechaVencimiento: String,
    onFechaVencimientoChange: (String) -> Unit,
    categoriaId: Int?,
    onCategoriaSelected: (Int) -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        FormFieldMolecule(
            label = "Nombre del producto",
            value = nombre,
            onValueChange = onNombreChange,
            placeholder = "Ej: Chocolate",
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormFieldMolecule(
            label = "Cantidad",
            value = cantidad,
            onValueChange = onCantidadChange,
            placeholder = "Ej: 10",
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormFieldMolecule(
            label = "Fecha de vencimiento",
            value = fechaVencimiento,
            onValueChange = onFechaVencimientoChange,
            placeholder = "YYYY-MM-DD",
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(32.dp))

        ActionButtonsMolecule(
            onCancel = onCancel,
            onSave = onSave,
            isLoading = isLoading
        )
    }
}