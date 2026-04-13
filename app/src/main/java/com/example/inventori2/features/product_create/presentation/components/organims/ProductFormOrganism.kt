package com.example.inventori2.features.product_create.presentation.components.organims

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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
    imageUri: Uri?,
    onImageSelected: (Uri?) -> Unit,
    getCameraUri: () -> Uri?,
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
    val categoriaSeleccionada = categorias.find { it.id == categoriaId }?.nombre ?: "Seleccionar categoría"

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        onImageSelected(uri)
    }

    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) onImageSelected(tempCameraUri)
    }

    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colorScheme.surfaceVariant.copy(alpha = 0.5f))
                .border(1.dp, colorScheme.outlineVariant, RoundedCornerShape(16.dp))
                .clickable { galleryLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                FilledIconButton(
                    onClick = { 
                        tempCameraUri = getCameraUri()
                        tempCameraUri?.let { cameraLauncher.launch(it) }
                    },
                    modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = colorScheme.primary)
                ) {
                    Icon(Icons.Default.AddAPhoto, contentDescription = null, modifier = Modifier.size(20.dp))
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Image, 
                        contentDescription = null, 
                        modifier = Modifier.size(40.dp), 
                        tint = colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Añadir foto del producto", 
                        color = colorScheme.onSurfaceVariant, 
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        FormFieldMolecule(
            label = "Nombre del producto",
            value = nombre,
            onValueChange = onNombreChange,
            placeholder = "Ej: Chocolate",
            enabled = !isLoading
        )

        FormFieldMolecule(
            label = "Cantidad",
            value = cantidad,
            onValueChange = onCantidadChange,
            placeholder = "Ej: 10",
            enabled = !isLoading
        )

        FormFieldMolecule(
            label = "Fecha de vencimiento",
            value = fechaVencimiento,
            onValueChange = onFechaVencimientoChange,
            placeholder = "YYYY-MM-DD",
            enabled = !isLoading
        )

        Column {
            Text(
                "Categoría", 
                style = MaterialTheme.typography.labelLarge, 
                color = colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { if(!isLoading) expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = categoriaSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        focusedBorderColor = colorScheme.primary,
                        unfocusedBorderColor = colorScheme.outlineVariant,
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface
                    ),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                )
                ExposedDropdownMenu(
                    expanded = expanded, 
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(colorScheme.surface)
                ) {
                    categorias.forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria.nombre, color = colorScheme.onSurface) },
                            onClick = {
                                onCategoriaSelected(categoria.id)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ActionButtonsMolecule(
            onCancel = onCancel,
            onSave = onSave,
            isLoading = isLoading,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}
