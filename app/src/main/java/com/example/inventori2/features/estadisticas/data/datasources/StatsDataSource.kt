package com.example.inventori2.features.estadisticas.data.datasources

import com.example.inventori2.features.estadisticas.domain.entities.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StatsDataSource @Inject constructor() {
    fun getStats(): Flow<StatsData> = flow {
        // Simulando datos reales basados en la imagen
        emit(
            StatsData(
                ahorroEstimado = 42.50,
                ahorroTendencia = "+12% vs mes anterior",
                desperdicioEvitado = "Has evitado el desperdicio de 3.2kg de alimentos.",
                consumoSemanal = listOf(
                    DailyConsumption("Lun", 4f, 3f),
                    DailyConsumption("Mar", 6f, 5f),
                    DailyConsumption("Mie", 3f, 4f),
                    DailyConsumption("Jue", 8f, 6f),
                    DailyConsumption("Vie", 5f, 7f),
                    DailyConsumption("Sab", 9f, 8f),
                    DailyConsumption("Dom", 4f, 5f)
                ),
                usoPorCategoria = listOf(
                    CategoryUsage("Lácteos", 45f, 0xFF22C55E),
                    CategoryUsage("Vegetales", 30f, 0xFF3B82F6),
                    CategoryUsage("Carnes", 25f, 0xFF1F2937)
                ),
                masUsado = ItemStat("Leche Semidesnatada", "Lácteos"),
                evitado = ItemStat("Tomate Cherry", "Vegetales")
            )
        )
    }
}
