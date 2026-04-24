package com.example.inventori2.features.inventory.product_edit.presentation.components.molecules

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventori2.features.inventory.product_create.presentation.components.atoms.PrimaryButtonAtom
import com.example.inventori2.features.inventory.product_create.presentation.components.atoms.SecondaryButtonAtom

@Composable
fun ActionButtonsMolecule(
    onCancel: () -> Unit,
    onSave: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        SecondaryButtonAtom(
            text = "Cancelar",
            onClick = onCancel,
            modifier = Modifier.weight(1f),
            enabled = !isLoading
        )
        Spacer(modifier = Modifier.width(16.dp))
        PrimaryButtonAtom(
            text = if (isLoading) "Guardando..." else "Guardar cambios",
            onClick = onSave,
            modifier = Modifier.weight(1f)
        )
    }
}