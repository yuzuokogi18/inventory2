package com.example.inventori2.features.product_detail.presentation.components.organims


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.material.icons.outlined.Label
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.inventori2.features.product_create.domain.entities.ProductCreate
import com.example.inventori2.features.product_detail.presentation.components.molecules.DetailRowMolecule
import com.example.inventori2.features.product_detail.presentation.components.molecules.StatusRowMolecule

@Composable
fun ProductDetailOrganism(
    product: ProductCreate,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        DetailRowMolecule(
            icon = Icons.Outlined.Label,
            label = "Nombre",
            value = product.nombre
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailRowMolecule(
            icon = Icons.Outlined.Inventory,
            label = "Cantidad",
            value = product.cantidad.toString()
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailRowMolecule(
            icon = Icons.Outlined.CalendarToday,
            label = "Fecha de vencimiento",
            value = product.fecha_vencimiento
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailRowMolecule(
            icon = Icons.Outlined.Category,
            label = "Categoría",
            value = product.categoriaId?.toString() ?: "No asignada"
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailRowMolecule(
            icon = Icons.Outlined.Person,
            label = "Usuario",
            value = product.usuarioId.toString()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Corregido: Se cambió 'isActive = true' por 'isExpired = false'
        StatusRowMolecule(isExpired = false)

        Spacer(modifier = Modifier.height(16.dp))

        DetailRowMolecule(
            icon = Icons.Outlined.CalendarToday,
            label = "Creado",
            value = formatDate(product.createdAt)
        )
    }
}

private fun formatDate(dateString: String?): String {
    if (dateString.isNullOrBlank()) return "Desconocido"
    return try {
        val parts = dateString.split("T").firstOrNull()?.split("-")
        if (parts != null && parts.size == 3) {
            "${parts[2]} de ${getMonthName(parts[1].toInt())} de ${parts[0]}"
        } else dateString
    } catch (e: Exception) {
        dateString
    }
}

private fun getMonthName(month: Int): String {
    return when (month) {
        1 -> "enero"
        2 -> "febrero"
        3 -> "marzo"
        4 -> "abril"
        5 -> "mayo"
        6 -> "junio"
        7 -> "julio"
        8 -> "agosto"
        9 -> "septiembre"
        10 -> "octubre"
        11 -> "noviembre"
        12 -> "diciembre"
        else -> ""
    }
}