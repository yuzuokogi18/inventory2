package com.example.inventori2.features.estadisticas.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventori2.core.ui.components.MainScaffold
import com.example.inventori2.features.dashboard.presentation.screens.DashboardBottomNav
import com.example.inventori2.features.estadisticas.presentation.viewmodels.StatsViewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.shape.rounded
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries

@Composable
fun StatsScreen(
    onNavigateToInicio: () -> Unit,
    onNavigateToInventory: () -> Unit,
    onNavigateToShopping: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: StatsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTimeframe by remember { mutableStateOf("Semana") }
    val timeframes = listOf("Día", "Semana", "Mes", "Año")

    MainScaffold(
        topAppBar = { StatsTopBar() },
        bottomAppBar = {
            DashboardBottomNav(
                onInicioClick = onNavigateToInicio,
                onInventarioClick = onNavigateToInventory,
                onComprasClick = onNavigateToShopping,
                onEstadisticasClick = { /* Ya estamos aquí */ },
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
                // SELECTOR DE TIEMPO
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    timeframes.forEach { timeframe ->
                        val isSelected = selectedTimeframe == timeframe
                        Surface(
                            modifier = Modifier.weight(1f).clickable { selectedTimeframe = timeframe },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Color(0xFF22C55E) else Color(0xFFF3F4F6)
                        ) {
                            Text(
                                text = timeframe,
                                modifier = Modifier.padding(vertical = 8.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else Color.Gray
                            )
                        }
                    }
                }

                uiState.data?.let { data ->
                    AhorroCard(data.ahorroEstimado, data.ahorroTendencia, data.desperdicioEvitado)
                    Spacer(modifier = Modifier.height(24.dp))

                    // CONSUMO SEMANAL CON DESCRIPCIÓN
                    StatsSectionCard(
                        title = "Consumo Semanal", 
                        subtitle = "Comparativa de productos utilizados",
                        description = "Este gráfico compara tu consumo diario de productos frente a la semana pasada, permitiéndote identificar patrones de ahorro."
                    ) {
                        Column {
                            // Leyenda de colores
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                LegendItem(color = Color(0xFF22C55E), text = "Esta Semana")
                                Spacer(Modifier.width(16.dp))
                                LegendItem(color = Color(0xFF3B82F6), text = "Semana Pasada")
                            }
                            VicoBarChart(data.consumoSemanal)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // USO POR CATEGORÍA
                    StatsSectionCard(
                        title = "Uso por Categoría", 
                        subtitle = "Distribución de productos más usados",
                        description = "Visualiza el porcentaje de productos gestionados por cada categoría en tu inventario."
                    ) {
                        CategoryDonutChart()
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        SmallStatBox(title = "MÁS USADO", name = data.masUsado.nombre, category = data.masUsado.categoria, modifier = Modifier.weight(1f))
                        SmallStatBox(title = "EVITADO", name = data.evitado.nombre, category = data.evitado.categoria, modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(10.dp).background(color, RoundedCornerShape(2.dp)))
        Text(text = text, modifier = Modifier.padding(start = 6.dp), fontSize = 11.sp, color = Color.Gray)
    }
}

@Composable
fun VicoBarChart(data: List<com.example.inventori2.features.estadisticas.domain.entities.DailyConsumption>) {
    val modelProducer = remember { CartesianChartModelProducer() }
    
    LaunchedEffect(data) {
        modelProducer.runTransaction {
            columnSeries {
                series(data.map { it.actual })
                series(data.map { it.pasada })
            }
        }
    }

    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberColumnCartesianLayer(
                columnProvider = com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer.ColumnProvider.series(
                    rememberLineComponent(color = Color(0xFF22C55E), thickness = 8.dp, shape = com.patrykandpatrick.vico.core.common.shape.Shape.rounded(4.dp)),
                    rememberLineComponent(color = Color(0xFF3B82F6), thickness = 8.dp, shape = com.patrykandpatrick.vico.core.common.shape.Shape.rounded(4.dp))
                )
            ),
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(
                valueFormatter = { value, _, _ -> data.getOrNull(value.toInt())?.dia ?: "" }
            ),
        ),
        modelProducer = modelProducer,
        modifier = Modifier.fillMaxWidth().height(200.dp)
    )
}

@Composable
fun AhorroCard(monto: Double, tendencia: String, mensaje: String) {
    Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFFF0FDF4), shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(36.dp).background(Color(0xFFDCFCE7), CircleShape), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.TrendingUp, contentDescription = null, tint = Color(0xFF22C55E), size = 20.dp)
                }
                Spacer(Modifier.width(12.dp))
                Text("Ahorro Estimado", color = Color(0xFF166534), fontWeight = FontWeight.Medium)
            }
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text("$${String.format("%.2f", monto)}", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
                Text(tendencia, color = Color(0xFF22C55E), fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp, bottom = 6.dp))
            }
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Eco, contentDescription = null, tint = Color(0xFF22C55E), size = 16.dp)
                Text(mensaje, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun StatsSectionCard(
    title: String, 
    subtitle: String, 
    description: String = "",
    content: @Composable () -> Unit
) {
    Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(subtitle, color = Color.Gray, fontSize = 12.sp)
            if (description.isNotEmpty()) {
                Text(
                    text = description, 
                    color = Color.LightGray, 
                    fontSize = 11.sp, 
                    lineHeight = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            content()
        }
    }
}

@Composable
fun CategoryDonutChart() {
    Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.size(120.dp).border(20.dp, Color(0xFF22C55E), CircleShape))
        Text("45%\nLácteos", textAlign = TextAlign.Center, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SmallStatBox(title: String, name: String, category: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shape = RoundedCornerShape(12.dp), border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6))) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(name, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            Surface(color = Color(0xFFF0FDF4), shape = RoundedCornerShape(8.dp), modifier = Modifier.padding(top = 4.dp)) {
                Text(category, color = Color(0xFF22C55E), fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }
        }
    }
}

@Composable
fun StatsTopBar() {
    Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = 20.dp, vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text("Estadísticas", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        IconButton(onClick = { }) { Icon(Icons.Default.CalendarToday, contentDescription = "Calendario", tint = Color.Gray) }
    }
}

@Composable
private fun Icon(imageVector: ImageVector, contentDescription: String?, modifier: Modifier = Modifier, size: androidx.compose.ui.unit.Dp = 24.dp, tint: Color = Color.Unspecified) {
    androidx.compose.material3.Icon(imageVector = imageVector, contentDescription = contentDescription, modifier = modifier.size(size), tint = tint)
}
