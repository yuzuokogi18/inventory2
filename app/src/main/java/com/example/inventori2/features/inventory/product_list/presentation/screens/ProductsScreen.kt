package com.example.inventori2.features.inventory.product_list.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventori2.core.ui.components.MainScaffold
import com.example.inventori2.features.dashboard.presentation.screens.DashboardBottomNav
import com.example.inventori2.features.inventory.product_delete.presentation.screens.ProductDeleteScreen
import com.example.inventori2.features.inventory.product_delete.presentation.viewmodels.ProductDeleteViewModel
import com.example.inventori2.features.inventory.product_list.presentation.components.organims.ProductCard
import com.example.inventori2.features.inventory.product_list.presentation.components.organims.ProductsTopBar
import com.example.inventori2.features.inventory.product_list.presentation.viewmodels.ProductViewModel

@Composable
fun ProductsScreen(
    onNavigateToInicio: () -> Unit = {},
    onNavigateToShopping: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onEditClick: (Int) -> Unit = {},
    onViewClick: (Int) -> Unit = {},
    onCreateProductClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = hiltViewModel(),
    deleteViewModel: ProductDeleteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val deleteError by deleteViewModel.error.collectAsStateWithLifecycle()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }
    val categories = listOf("Todos", "Lácteos", "Frutas", "Verduras", "Carnes", "Bebidas", "Snacks")

    val snackbarHostState = remember { SnackbarHostState() }
    var showDeleteScreen by remember { mutableStateOf(false) }
    var selectedProductId by remember { mutableIntStateOf(-1) }
    var selectedProductName by remember { mutableStateOf("") }

    val filteredProducts = uiState.products.filter { product ->
        val matchesSearch = product.nombre.contains(searchQuery, ignoreCase = true)
        val matchesCategory = if (selectedCategory == "Todos") {
            true 
        } else {
            val catName = when(product.categoriaId) {
                1 -> "Bebidas"
                2 -> "Lácteos"
                3 -> "Carnes"
                4 -> "Frutas" 
                5 -> "Snacks"
                else -> "Otros"
            }
            catName == selectedCategory || (selectedCategory == "Verduras" && product.categoriaId == 4)
        }
        matchesSearch && matchesCategory
    }

    val criticalCount = uiState.products.count { it.cantidad <= 3 }

    LaunchedEffect(Unit) {
        viewModel.getProducts()
    }

    LaunchedEffect(deleteError) {
        deleteError?.let {
            snackbarHostState.showSnackbar(it)
            deleteViewModel.clearError()
        }
    }

    ProductDeleteScreen(
        show = showDeleteScreen,
        productId = selectedProductId,
        productName = selectedProductName,
        viewModel = deleteViewModel,
        onDismiss = { showDeleteScreen = false },
        onDeleteSuccess = {
            showDeleteScreen = false
            viewModel.getProducts()
        }
    )

    MainScaffold(
        topAppBar = { ProductsTopBar(title = "Inventario") },
        bottomAppBar = { 
            DashboardBottomNav(
                onInicioClick = onNavigateToInicio,
                onInventarioClick = { /* Ya estamos aquí */ },
                onComprasClick = onNavigateToShopping,
                onEstadisticasClick = { /* TODO */ },
                onMasClick = onNavigateToProfile
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 0.dp)
            ) {
                // 1. BARRA DE BÚSQUEDA
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    placeholder = { Text("Buscar productos...", color = Color.Gray) },
                    leadingIcon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null, tint = Color.Gray) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF9FAFB),
                        focusedContainerColor = Color(0xFFF9FAFB),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color(0xFF22C55E)
                    ),
                    singleLine = true
                )

                // 2. FILTROS DE CATEGORÍA
                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        val isSelected = selectedCategory == category
                        Surface(
                            modifier = Modifier.clickable { selectedCategory = category },
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) Color(0xFF22C55E) else Color.White,
                            border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB))
                        ) {
                            Text(
                                text = category,
                                color = if (isSelected) Color.White else Color.Gray,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                // 3. BANNER DE ESTADO
                Surface(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF0FDF4)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(40.dp).background(Color(0xFFDCFCE7), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Outlined.Inventory2, contentDescription = null, tint = Color(0xFF22C55E))
                        }
                        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                            Text("TOTAL PRODUCTOS", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Text("${filteredProducts.size} Ítems", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF166534))
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("$criticalCount Críticos", fontSize = 12.sp, color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                            Text("Revisar pronto", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }

                Text(
                    "TU DESPENSA",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    letterSpacing = 1.sp
                )

                // 4. LISTA DE PRODUCTOS
                if (uiState.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF22C55E))
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(filteredProducts) { product ->
                            ProductCard(
                                title = product.nombre,
                                cantidad = product.cantidad,
                                fechaVencimiento = product.fechaVencimiento,
                                imagenUri = product.imagenUri, // CORREGIDO: Ahora pasamos la imagen
                                categoria = when(product.categoriaId) {
                                    1 -> "Bebidas" 2 -> "Lácteos" 3 -> "Carnes" 4 -> "Frutas" 5 -> "Snacks" else -> "General"
                                },
                                onEditClick = { onEditClick(product.id) },
                                onViewClick = { onViewClick(product.id) },
                                onDeleteClick = {
                                    selectedProductId = product.id
                                    selectedProductName = product.nombre
                                    showDeleteScreen = true
                                }
                            )
                        }
                        item { Spacer(modifier = Modifier.height(100.dp)) }
                    }
                }
            }

            // BOTÓN FLOTANTE (FAB)
            FloatingActionButton(
                onClick = onCreateProductClick,
                containerColor = Color(0xFF22C55E),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp)
                    .size(60.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir", modifier = Modifier.size(30.dp))
            }
        }
    }
}
