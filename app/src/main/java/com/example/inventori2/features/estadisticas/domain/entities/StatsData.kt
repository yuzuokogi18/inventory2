package com.example.inventori2.features.estadisticas.domain.entities

data class StatsData(
    val ahorroEstimado: Double,
    val ahorroTendencia: String,
    val desperdicioEvitado: String,
    val consumoSemanal: List<DailyConsumption>,
    val usoPorCategoria: List<CategoryUsage>,
    val masUsado: ItemStat,
    val evitado: ItemStat
)

data class DailyConsumption(
    val dia: String,
    val actual: Float,
    val pasada: Float
)

data class CategoryUsage(
    val nombre: String,
    val porcentaje: Float,
    val color: Long
)

data class ItemStat(
    val nombre: String,
    val categoria: String
)
