package com.example.inventori2.features.dashboard.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.core.datastore.TokenDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardState(
    val totalProducts: Int = 0,
    val lowStockProducts: Int = 0,
    val isLoading: Boolean = true
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val productDao: ProductDao,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState = _uiState.asStateFlow()

    init {
        loadStatistics()
    }

    private fun loadStatistics() {
        viewModelScope.launch {

            tokenDataStore.getUser().filterNotNull().flatMapLatest { user ->
                productDao.getAllProducts(user.id)
            }.collect { products ->
                _uiState.update { 
                    it.copy(
                        totalProducts = products.size,
                        lowStockProducts = products.count { p -> p.cantidad < 5 },
                        isLoading = false
                    )
                }
            }
        }
    }
}
