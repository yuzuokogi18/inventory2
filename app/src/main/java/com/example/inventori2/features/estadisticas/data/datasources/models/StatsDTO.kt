package com.example.inventori2.features.estadisticas.data.datasources.models

data class StatsDTO(
    val ahorroEstimado: Double,
    val ahorroTendencia: String,
    val desperdicioEvitado: String,
    val consumoSemanal: List<DailyConsumptionDTO>,
    val usoPorCategoria: List<CategoryUsageDTO>,
    val masUsado: ItemStatDTO,
    val evitado: ItemStatDTO
)

data class DailyConsumptionDTO(
    val dia: String,
    val actual: Float,
    val pasada: Float
)

data class CategoryUsageDTO(
    val nombre: String,
    val porcentaje: Float,
    val color: Long
)

data class ItemStatDTO(
    val nombre: String,
    val categoria: String
)
