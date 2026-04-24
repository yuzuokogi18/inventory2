package com.example.inventori2.features.estadisticas.data.datasources.mapper

import com.example.inventori2.features.estadisticas.data.datasources.models.*
import com.example.inventori2.features.estadisticas.domain.entities.*

fun StatsDTO.toDomain(): StatsData {
    return StatsData(
        ahorroEstimado = ahorroEstimado,
        ahorroTendencia = ahorroTendencia,
        desperdicioEvitado = desperdicioEvitado,
        consumoSemanal = consumoSemanal.map { it.toDomain() },
        usoPorCategoria = usoPorCategoria.map { it.toDomain() },
        masUsado = masUsado.toDomain(),
        evitado = evitado.toDomain()
    )
}

fun DailyConsumptionDTO.toDomain() = DailyConsumption(dia, actual, pasada)

fun CategoryUsageDTO.toDomain() = CategoryUsage(nombre, porcentaje, color)

fun ItemStatDTO.toDomain() = ItemStat(nombre, categoria)
