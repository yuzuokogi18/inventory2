package com.example.inventori2.features.estadisticas.domain.repositories

import com.example.inventori2.features.estadisticas.domain.entities.StatsData
import kotlinx.coroutines.flow.Flow

interface StatsRepository {
    fun getStats(): Flow<StatsData>
}
