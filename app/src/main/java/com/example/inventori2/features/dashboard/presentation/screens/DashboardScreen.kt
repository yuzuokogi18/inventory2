package com.example.inventori2.features.dashboard.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.inventori2.core.ui.components.MainScaffold
import com.example.inventori2.features.dashboard.presentation.viewmodels.DashboardViewModel
import com.example.inventori2.features.notification.presentation.viewmodels.NotificationViewModel

@Composable
fun DashboardScreen(
    onNavigateToList: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToShopping: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel(),
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val notificationState by notificationViewModel.uiState.collectAsStateWithLifecycle()

    MainScaffold(
        topAppBar = { 
            DashboardTopBar(
                unreadCount = notificationState.unreadCount,
                onNotificationClick = onNavigateToNotifications
            ) 
        },
        bottomAppBar = { 
            DashboardBottomNav(
                onInicioClick = { /* Ya estamos aquí */ },
                onInventarioClick = onNavigateToList,
                onComprasClick = onNavigateToShopping,
                onEstadisticasClick = onNavigateToStats,
                onMasClick = onNavigateToProfile
            ) 
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                // HEADER: Saludo y Perfil
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Hola, Usuario", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                            Spacer(Modifier.width(6.dp))
                            Text("👋", fontSize = 22.sp)
                        }
                        Text("Tu despensa está al 84% de capacidad.", color = Color.Gray, fontSize = 14.sp)
                    }
                    AsyncImage(
                        model = "https://via.placeholder.com/150", 
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .clickable { onNavigateToProfile() },
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // ESTADO GENERAL
                Text("ESTADO GENERAL", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(14.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatCard(
                        value = uiState.totalProducts.toString(),
                        label = "TOTAL",
                        sublabel = "Inventario real",
                        icon = Icons.Outlined.Inventory2,
                        iconColor = Color(0xFF22C55E),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        value = uiState.nearExpiryProducts.toString(),
                        label = "POR CADUCAR",
                        sublabel = "Vencen pronto",
                        icon = Icons.Outlined.Schedule,
                        iconColor = Color(0xFFEF4444),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        value = uiState.lowStockProducts.toString(),
                        label = "STOCK BAJO",
                        sublabel = "Pocas unidades",
                        icon = Icons.Outlined.ReportProblem,
                        iconColor = Color(0xFFF59E0B),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ALERTAS RECIENTES
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Alertas Recientes", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "Ver todas", 
                        fontSize = 14.sp, 
                        color = Color(0xFF22C55E), 
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onNavigateToList() }
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.alerts.isEmpty()) {
                    Text("No hay alertas pendientes", color = Color.LightGray, modifier = Modifier.padding(vertical = 16.dp))
                } else {
                    uiState.alerts.forEach { alert ->
                        AlertItem(
                            name = alert.name,
                            status = alert.status,
                            tag = alert.tag,
                            tagColor = if (alert.tag == "Caduca") Color(0xFFEF4444) else Color(0xFF3B82F6)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                uiState.suggestion?.let { suggestion ->
                    SugerenciaBanner(
                        message = suggestion.message,
                        onGoToShopping = {
                            viewModel.addSuggestedToShopping()
                            onNavigateToShopping()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(100.dp)) 
            }

            // BOTÓN FLOTANTE (FAB)
            FloatingActionButton(
                onClick = onNavigateToCreate,
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

@Composable
fun DashboardTopBar(
    unreadCount: Int,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp).background(Color(0xFF22C55E), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        BadgedBox(
            badge = { 
                if (unreadCount > 0) {
                    Badge { Text(unreadCount.toString()) }
                }
            },
            modifier = Modifier.clickable { onNotificationClick() }
        ) {
            Icon(Icons.Outlined.Notifications, contentDescription = "Alertas", modifier = Modifier.size(26.dp), tint = Color.Gray)
        }
    }
}

@Composable
fun StatCard(
    value: String,
    label: String,
    sublabel: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(140.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6)),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Box(
                modifier = Modifier.size(32.dp).background(iconColor.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
            Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray, maxLines = 1)
            Text(sublabel, fontSize = 9.sp, color = Color.LightGray, fontWeight = FontWeight.Medium, maxLines = 1)
        }
    }
}

@Composable
fun AlertItem(name: String, status: String, tag: String, tagColor: Color) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6)),
        shadowElevation = 0.5.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(44.dp).background(Color(0xFFF9FAFB), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Image, contentDescription = null, modifier = Modifier.size(22.dp), tint = Color.LightGray)
            }
            Column(modifier = Modifier.weight(1f).padding(horizontal = 10.dp)) {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(status, fontSize = 12.sp, color = Color.Gray)
            }
            Surface(color = tagColor, shape = RoundedCornerShape(12.dp)) {
                Text(tag, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
            }
            Spacer(Modifier.width(4.dp))
            Icon(Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.LightGray)
        }
    }
}

@Composable
fun SugerenciaBanner(message: String, onGoToShopping: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFF0FDF4),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(color = Color(0xFFDCFCE7), shape = RoundedCornerShape(8.dp)) {
                Text("Sugerencia Pro", color = Color(0xFF166534), fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text("Ahorra hasta un 15%", fontWeight = FontWeight.ExtraBold, fontSize = 17.sp)
            Text(
                text = message,
                fontSize = 13.sp, color = Color.DarkGray, lineHeight = 18.sp, modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                "Ir a Lista de Compras", color = Color(0xFF22C55E), fontWeight = FontWeight.Bold, fontSize = 14.sp, 
                modifier = Modifier.padding(top = 10.dp).clickable { onGoToShopping() }
            )
        }
    }
}

@Composable
fun DashboardBottomNav(
    onInicioClick: () -> Unit,
    onInventarioClick: () -> Unit,
    onComprasClick: () -> Unit,
    onEstadisticasClick: () -> Unit,
    onMasClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.height(65.dp)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = null, modifier = Modifier.size(24.dp)) },
            label = { Text("Inicio", fontSize = 9.sp, maxLines = 1, softWrap = false) },
            selected = true,
            onClick = onInicioClick,
            colors = NavigationBarItemDefaults.colors(selectedIconColor = Color(0xFF22C55E), selectedTextColor = Color(0xFF22C55E), indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.ContentPaste, contentDescription = null, modifier = Modifier.size(24.dp)) },
            label = { Text("Inventario", fontSize = 9.sp, maxLines = 1, softWrap = false) },
            selected = false,
            onClick = onInventarioClick
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.ShoppingCart, contentDescription = null, modifier = Modifier.size(24.dp)) },
            label = { Text("Compras", fontSize = 9.sp, maxLines = 1, softWrap = false) },
            selected = false,
            onClick = onComprasClick
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.BarChart, contentDescription = null, modifier = Modifier.size(24.dp)) },
            label = { Text("Estadísticas", fontSize = 9.sp, maxLines = 1, softWrap = false) },
            selected = false,
            onClick = onEstadisticasClick
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.MoreHoriz, contentDescription = null, modifier = Modifier.size(24.dp)) },
            label = { Text("Más", fontSize = 9.sp, maxLines = 1, softWrap = false) },
            selected = false,
            onClick = onMasClick
        )
    }
}

@Composable
private fun Icon(imageVector: ImageVector, contentDescription: String?, modifier: Modifier = Modifier, size: androidx.compose.ui.unit.Dp = 24.dp, tint: Color = Color.Unspecified) {
    androidx.compose.material3.Icon(imageVector = imageVector, contentDescription = contentDescription, modifier = modifier.size(size), tint = tint)
}
