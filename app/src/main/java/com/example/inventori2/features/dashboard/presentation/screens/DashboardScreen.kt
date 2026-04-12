package com.example.inventori2.features.dashboard.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventori2.features.dashboard.presentation.viewmodels.DashboardViewModel
import com.example.inventori2.ui.theme.*

@Composable
fun DashboardScreen(
    onNavigateToList: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = { /* Aquí irá el Bottom Navigation más adelante */ }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                HeaderSection(userName = "Usuario", onProfileClick = onNavigateToProfile)
                Spacer(modifier = Modifier.height(24.dp))
                CapacityCard(capacityValue = 0.85f)
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "ESTADO GENERAL",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCardSmall(
                        title = "TOTAL",
                        value = uiState.totalProducts.toString(),
                        icon = Icons.Default.Inventory2,
                        color = PrimaryGreen,
                        modifier = Modifier.weight(1f)
                    )
                    StatCardSmall(
                        title = "POR CADUCAR",
                        value = "5", // Hardcoded for UI demo
                        icon = Icons.Default.Timer,
                        color = AlertRed,
                        modifier = Modifier.weight(1f)
                    )
                    StatCardSmall(
                        title = "STOCK BAJO",
                        value = uiState.lowStockProducts.toString(),
                        icon = Icons.Default.TrendingDown,
                        color = WarningOrange,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Alertas Recientes",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { /* Navigate to Alerts */ }) {
                        Text("Ver todas", color = PrimaryGreen)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Simulación de alertas del diseño
            items(recentAlerts) { alert ->
                AlertItem(alert)
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                SupermarketCard()
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun HeaderSection(userName: String, onProfileClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Hola, $userName 👋",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Tu despensa está al 85% de capacidad",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
        IconButton(
            onClick = onProfileClick,
            modifier = Modifier
                .size(45.dp)
                .background(Color.White, CircleShape)
        ) {
            Icon(Icons.Default.Person, contentDescription = "Perfil", tint = TextSecondary)
        }
    }
}

@Composable
fun CapacityCard(capacityValue: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp)) {
                Canvas(modifier = Modifier.size(80.dp)) {
                    drawArc(
                        color = BorderColor,
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = PrimaryGreen,
                        startAngle = -90f,
                        sweepAngle = 360f * capacityValue,
                        useCenter = false,
                        style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text(
                    text = "${(capacityValue * 100).toInt()}%",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(text = "Estado Óptimo", fontWeight = FontWeight.Bold, color = PrimaryGreen)
                Text(
                    text = "Tienes suficiente espacio para más productos.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
fun StatCardSmall(title: String, value: String, icon: ImageVector, color: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(text = title, fontSize = 10.sp, color = TextSecondary, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun AlertItem(alert: RecentAlert) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(alert.color.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(alert.icon, contentDescription = null, tint = alert.color)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = alert.title, fontWeight = FontWeight.Bold)
                Text(text = alert.subtitle, fontSize = 12.sp, color = TextSecondary)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = BorderColor)
        }
    }
}

@Composable
fun SupermarketCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Supermercado", color = PrimaryGreen, fontWeight = FontWeight.Bold)
                Text(
                    "Ahorra hasta un 15% en productos de temporada.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
            Icon(Icons.Default.ArrowForward, contentDescription = null, tint = PrimaryGreen)
        }
    }
}

data class RecentAlert(val title: String, val subtitle: String, val icon: ImageVector, val color: Color)
val recentAlerts = listOf(
    RecentAlert("Leche Entera 1L", "Caduca mañana", Icons.Default.ErrorOutline, AlertRed),
    RecentAlert("Huevos Docena", "Solo quedan 3 unidades", Icons.Default.WarningAmber, WarningOrange),
    RecentAlert("Yogurt Griego", "Caducó hace 2 días", Icons.Default.History, AlertRed)
)
