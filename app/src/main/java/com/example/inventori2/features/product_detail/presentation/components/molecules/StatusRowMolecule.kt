package com.example.inventori2.features.product_detail.presentation.components.molecules



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import com.example.inventori2.core.theme.ui.EduTrackTheme
import com.example.inventori2.features.product_detail.presentation.components.atoms.IconLabelAtom
import com.example.inventori2.features.product_detail.presentation.components.atoms.StatusBadgeAtom

@Composable
fun StatusRowMolecule(
    isExpired: Boolean,
    modifier: Modifier = Modifier

) {
    Column(modifier = modifier.fillMaxWidth()) {
        IconLabelAtom(
            icon = Icons.Outlined.Warning,
            label = "Estado del producto"
        )
        Spacer(modifier = Modifier.height(8.dp))
        // CORRECCIÓN: Cambiamos isActive por isExpired para que coincida con StatusBadgeAtom
        StatusBadgeAtom(isExpired = isExpired) 
    }
}

@Preview(showBackground = true, name = "Estado: Vigente")
@Composable
fun PreviewStatusRowMoleculeVigente() {
    EduTrackTheme {
        StatusRowMolecule(isExpired = false)
    }
}

@Preview(showBackground = true, name = "Estado: Expirado")
@Composable
fun PreviewStatusRowMoleculeExpirado() {
    EduTrackTheme {
        StatusRowMolecule(isExpired = true)
    }
}
