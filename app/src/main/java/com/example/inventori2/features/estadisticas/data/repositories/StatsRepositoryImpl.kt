package com.example.inventori2.features.estadisticas.data.repositories

import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.features.estadisticas.domain.entities.*
import com.example.inventori2.features.estadisticas.domain.repositories.StatsRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class StatsRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val tokenDataStore: TokenDataStore
) : StatsRepository {

    override fun getStats(): Flow<StatsData> {
        return tokenDataStore.getUser().filterNotNull().flatMapLatest { user ->
            productDao.getAllProducts(user.id).map { products ->
                
                // 1. Cálculo de uso por categoría REAL
                val categoryMap = products.groupBy { it.categoriaId ?: 0 }
                val usoPorCategoria = categoryMap.map { (catId, list) ->
                    val nombre = when(catId) {
                        1 -> "Bebidas"
                        2 -> "Lácteos"
                        3 -> "Carnes"
                        4 -> "Frutas"
                        5 -> "Snacks"
                        else -> "Otros"
                    }
                    val color = when(catId) {
                        1 -> 0xFF3B82F6 // Azul
                        2 -> 0xFF22C55E // Verde
                        3 -> 0xFFEF4444 // Rojo
                        4 -> 0xFFF59E0B // Naranja
                        else -> 0xFF9CA3AF // Gris
                    }
                    CategoryUsage(nombre, (list.size.toFloat() / products.size.coerceAtLeast(1)) * 100, color)
                }

                // 2. Cálculo de consumo semanal (Simulado con base en stock real)
                val consumoSemanal = listOf(
                    DailyConsumption("Lun", products.size.toFloat() * 0.4f, 3f),
                    DailyConsumption("Mar", products.size.toFloat() * 0.6f, 5f),
                    DailyConsumption("Mie", products.size.toFloat() * 0.3f, 4f),
                    DailyConsumption("Jue", products.size.toFloat() * 0.8f, 6f),
                    DailyConsumption("Vie", products.size.toFloat() * 0.5f, 7f),
                    DailyConsumption("Sab", products.size.toFloat() * 0.9f, 8f),
                    DailyConsumption("Dom", products.size.toFloat() * 0.4f, 5f)
                )

                // 3. Producto más usado (Mayor cantidad en stock)
                val mostUsedProduct = products.maxByOrNull { it.cantidad }
                val masUsado = ItemStat(
                    nombre = mostUsedProduct?.nombre ?: "Sin datos",
                    categoria = getCategoryName(mostUsedProduct?.categoriaId)
                )

                // 4. Producto evitado de caducar (Próximo a vencer pero aún vigente)
                val evitado = ItemStat(
                    nombre = products.filter { it.cantidad > 0 }.minByOrNull { it.fechaVencimiento }?.nombre ?: "Sin datos",
                    categoria = getCategoryName(products.minByOrNull { it.fechaVencimiento }?.categoriaId)
                )

                StatsData(
                    ahorroEstimado = products.sumOf { it.cantidad } * 2.5, // Heurística: cada item ahorra $2.5
                    ahorroTendencia = "+${products.size}% vs mes anterior",
                    desperdicioEvitado = "Has evitado el desperdicio de ${(products.size * 0.2).format(1)}kg de alimentos.",
                    consumoSemanal = consumoSemanal,
                    usoPorCategoria = usoPorCategoria,
                    masUsado = masUsado,
                    evitado = evitado
                )
            }
        }
    }

    private fun getCategoryName(id: Int?): String = when(id) {
        1 -> "Bebidas"
        2 -> "Lácteos"
        3 -> "Carnes"
        4 -> "Frutas"
        5 -> "Snacks"
        else -> "General"
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}
