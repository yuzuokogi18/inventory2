package com.example.inventori2.features.estadisticas.domain.usecases

import com.example.inventori2.features.estadisticas.domain.entities.StatsData
import com.example.inventori2.features.estadisticas.domain.repositories.StatsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStatsUseCase @Inject constructor(
    private val repository: StatsRepository
) {
    operator fun invoke(): Flow<StatsData> = repository.getStats()
}
