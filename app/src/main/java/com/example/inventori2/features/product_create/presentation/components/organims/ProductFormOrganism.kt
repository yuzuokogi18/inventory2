package com.example.inventori2.features.product_create.presentation.components.organims

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventori2.features.product_create.presentation.components.molecules.ActionButtonsMolecule
import com.example.inventori2.features.product_create.presentation.components.molecules.FormFieldMolecule

data class Categoria(
    val id: Int,
    val nombre: String
)

@OptIn(ExperimentalMaterial3Api::class)
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

    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {

    val categorias = listOf(
        Categoria(1, "Bebidas"),
        Categoria(2, "Lácteos"),
        Categoria(3, "Carnes"),
        Categoria(4, "Frutas y Verduras"),
        Categoria(5, "Snacks")
    )

    var expanded by remember { mutableStateOf(false) }

    val categoriaSeleccionada =
        categorias.find { it.id == categoriaId }?.nombre ?: "Seleccionar categoría"

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        // 📦 Nombre
        FormFieldMolecule(
            label = "Nombre del producto",
            value = nombre,
            onValueChange = onNombreChange,
            placeholder = "Ej: Coca Cola",
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🔢 Cantidad
        FormFieldMolecule(
            label = "Cantidad",
            value = cantidad,
            onValueChange = onCantidadChange,
            placeholder = "Ej: 10",
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 📅 Fecha vencimiento
        FormFieldMolecule(
            label = "Fecha de vencimiento",
            value = fechaVencimiento,
            onValueChange = onFechaVencimientoChange,
            placeholder = "YYYY-MM-DD",
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🏷 Categoría Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {

            OutlinedTextField(
                value = categoriaSeleccionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoría") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                enabled = !isLoading
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categorias.forEach { categoria ->
                    DropdownMenuItem(
                        text = { Text(categoria.nombre) },
                        onClick = {
                            onCategoriaSelected(categoria.id)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 🔘 Botones
        ActionButtonsMolecule(
            onCancel = onCancel,
            onSave = onSave,
            isLoading = isLoading
        )
    }
}