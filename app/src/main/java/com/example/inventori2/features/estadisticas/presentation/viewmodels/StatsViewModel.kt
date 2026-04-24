package com.example.inventori2.features.estadisticas.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.features.estadisticas.domain.entities.StatsData
import com.example.inventori2.features.estadisticas.domain.usecases.GetStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StatsUiState(
    val data: StatsData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val getStatsUseCase: GetStatsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadStats()
    }

    fun loadStats() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getStatsUseCase().collect { stats ->
                _uiState.update { it.copy(
                    data = stats,
                    isLoading = false
                ) }
            }
        }
    }
}
