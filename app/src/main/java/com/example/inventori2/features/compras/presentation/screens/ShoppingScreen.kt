package com.example.inventori2.features.compras.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventori2.core.ui.components.MainScaffold
import com.example.inventori2.features.compras.domain.entities.ShoppingItem
import com.example.inventori2.features.compras.presentation.viewmodels.ShoppingViewModel
import com.example.inventori2.features.dashboard.presentation.screens.DashboardBottomNav

@Composable
fun ShoppingScreen(
    onNavigateToInicio: () -> Unit,
    onNavigateToInventory: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: ShoppingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var newItemName by remember { mutableStateOf("") }
    
    var showQuantityDialog by remember { mutableStateOf(false) }
    var selectedItemForAdd by remember { mutableStateOf<ShoppingItem?>(null) }
    var quantityToAdd by remember { mutableStateOf("1") }

    if (showQuantityDialog) {
        AlertDialog(
            onDismissRequest = { showQuantityDialog = false },
            title = { Text("Añadir ${selectedItemForAdd?.nombre}") },
            text = {
                Column {
                    Text("¿Cuántas unidades deseas comprar?")
                    OutlinedTextField(
                        value = quantityToAdd,
                        onValueChange = { quantityToAdd = it },
                        modifier = Modifier.padding(top = 8.dp),
                        label = { Text("Cantidad") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        selectedItemForAdd?.let { viewModel.addItem(it.nombre, "$quantityToAdd u") }
                        showQuantityDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E))
                ) { Text("Añadir") }
            }
        )
    }

    MainScaffold(
        topAppBar = { 
            ShoppingTopBar(
                onRefresh = { viewModel.syncWithInventory() }, 
                onDownload = { 
                    // Exportamos los productos que están en la lista (reales del usuario)
                    val itemsString = uiState.items.map { "${it.nombre} (${it.cantidad})" }
                    viewModel.descargarLista(itemsString)
                },
                onClearAll = { /* TODO */ } 
            ) 
        },
        bottomAppBar = {
            DashboardBottomNav(
                onInicioClick = onNavigateToInicio,
                onInventarioClick = onNavigateToInventory,
                onComprasClick = { /* Ya estamos aquí */ },
                onEstadisticasClick = { /* TODO */ },
                onMasClick = onNavigateToProfile
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
            Column(modifier = Modifier.fillMaxSize()) {
                // TÍTULO Y RESUMEN
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.ShoppingCart, contentDescription = null, size = 20.dp, tint = Color(0xFF22C55E))
                        Spacer(Modifier.width(8.dp))
                        Text("${uiState.items.size} artículos en tu lista", fontWeight = FontWeight.Bold)
                    }
                }

                Divider(color = Color(0xFFF3F4F6))

                LazyColumn(modifier = Modifier.weight(1f)) {
                    // SUGERENCIAS INTELIGENTES
                    item {
                        Column(modifier = Modifier.padding(vertical = 12.dp)) {
                            Row(modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.AutoAwesome, contentDescription = null, size = 16.dp, tint = Color(0xFF3B82F6))
                                Spacer(Modifier.width(8.dp))
                                Text("SUGERENCIAS INTELIGENTES", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            }
                            LazyRow(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                items(uiState.suggestions) { suggestion ->
                                    SuggestionCard(suggestion, onAdd = { 
                                        selectedItemForAdd = it
                                        showQuantityDialog = true 
                                    })
                                }
                            }
                        }
                    }

                    // AÑADIR MANUAL
                    item {
                        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = newItemName,
                                onValueChange = { newItemName = it },
                                modifier = Modifier.weight(1f),
                                placeholder = { Text("¿Qué necesitas comprar?", color = Color.Gray) },
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color(0xFFF9FAFB), focusedContainerColor = Color(0xFFF9FAFB), unfocusedBorderColor = Color(0xFFE5E7EB))
                            )
                            Spacer(Modifier.width(12.dp))
                            FloatingActionButton(
                                onClick = { 
                                    viewModel.addItem(newItemName)
                                    newItemName = "" 
                                },
                                containerColor = Color(0xFF22C55E), contentColor = Color.White, shape = RoundedCornerShape(12.dp), modifier = Modifier.size(52.dp)
                            ) { Icon(Icons.Default.Add, contentDescription = null) }
                        }
                    }

                    item { Text("PRODUCTOS ELEGIDOS PARA COMPRAR", modifier = Modifier.padding(16.dp), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray) }

                    // LA LISTA REAL DEL USUARIO
                    if (uiState.items.isEmpty()) {
                        item { 
                            Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                                Text("Tu lista está vacía", color = Color.LightGray)
                            }
                        }
                    } else {
                        items(uiState.items) { item ->
                            ShoppingListItem(item = item, onToggle = { viewModel.toggleComplete(item) }, onDelete = { viewModel.deleteItem(item.id) })
                        }
                    }

                    // BANNER AUTO
                    item {
                        Surface(modifier = Modifier.padding(16.dp).fillMaxWidth(), shape = RoundedCornerShape(12.dp), color = Color(0xFFF9FAFB), border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB))) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(32.dp).border(1.2.dp, Color.Gray, CircleShape), contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Check, contentDescription = null, size = 16.dp, tint = Color.Gray)
                                }
                                Column(modifier = Modifier.padding(start = 12.dp)) {
                                    Text("Actualización Automática", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text("Al marcar como comprado, el inventario se actualizará solo.", fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                        }
                    }
                    item { Spacer(Modifier.height(80.dp)) }
                }
            }

            // BOTÓN DE IMPORTACIÓN (Caja)
            FloatingActionButton(
                onClick = { viewModel.importToInventory() },
                containerColor = Color(0xFF22C55E), contentColor = Color.White, shape = CircleShape, modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp).size(60.dp)
            ) { Icon(Icons.Outlined.Inventory2, contentDescription = null, size = 28.dp) }
        }
    }
}

@Composable
fun ShoppingTopBar(onRefresh: () -> Unit, onDownload: () -> Unit, onClearAll: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Lista de Compras", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        Row {
            IconButton(onClick = onRefresh) { Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.Gray, size = 24.dp) }
            IconButton(onClick = onDownload) { Icon(Icons.Default.Download, contentDescription = "Descargar", tint = Color(0xFF22C55E), size = 24.dp) }
            IconButton(onClick = onClearAll) { Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color.Gray, size = 24.dp) }
        }
    }
}

@Composable
fun SuggestionCard(item: ShoppingItem, onAdd: (ShoppingItem) -> Unit) {
    Surface(modifier = Modifier.width(150.dp), shape = RoundedCornerShape(12.dp), color = Color(0xFFF0FDF4), border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDCFCE7))) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(item.nombre, fontWeight = FontWeight.Bold, fontSize = 15.sp, maxLines = 1)
            Text(item.cantidad, color = Color(0xFF22C55E), fontSize = 12.sp, modifier = Modifier.padding(vertical = 4.dp))
            Button(onClick = { onAdd(item) }, modifier = Modifier.fillMaxWidth().height(36.dp), contentPadding = PaddingValues(0.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E)), shape = RoundedCornerShape(8.dp)) {
                Icon(Icons.Default.Add, contentDescription = null, size = 16.dp); Spacer(Modifier.width(4.dp)); Text("Añadir", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ShoppingListItem(item: ShoppingItem, onToggle: () -> Unit, onDelete: () -> Unit) {
    Column {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = item.isCompleted, onCheckedChange = { onToggle() }, colors = CheckboxDefaults.colors(checkedColor = Color(0xFF22C55E)))
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Text(text = item.nombre, fontWeight = FontWeight.Bold, fontSize = 15.sp, textDecoration = if (item.isCompleted) TextDecoration.LineThrough else null, color = if (item.isCompleted) Color.Gray else Color.Unspecified)
                Text("${item.categoria} • ${item.cantidad}", fontSize = 12.sp, color = Color.Gray)
            }
            IconButton(onClick = onDelete) { Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color.LightGray, size = 20.dp) }
        }
        Divider(color = Color(0xFFF3F4F6))
    }
}

@Composable
private fun Icon(imageVector: ImageVector, contentDescription: String?, modifier: Modifier = Modifier, size: androidx.compose.ui.unit.Dp = 24.dp, tint: Color = Color.Unspecified) {
    androidx.compose.material3.Icon(imageVector = imageVector, contentDescription = contentDescription, modifier = modifier.size(size), tint = tint)
}
