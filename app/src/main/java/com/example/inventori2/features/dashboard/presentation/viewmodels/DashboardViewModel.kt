package com.example.inventori2.features.dashboard.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.features.compras.domain.entities.ShoppingItem
import com.example.inventori2.features.compras.domain.repositories.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class ProductAlert(
    val name: String,
    val status: String,
    val tag: String,
    val isCritical: Boolean
)

data class SmartSuggestion(
    val productName: String,
    val message: String,
    val discount: String = "15%"
)

data class DashboardState(
    val totalProducts: Int = 0,
    val lowStockProducts: Int = 0,
    val nearExpiryProducts: Int = 0,
    val alerts: List<ProductAlert> = emptyList(),
    val suggestion: SmartSuggestion? = null,
    val isLoading: Boolean = true
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val productDao: ProductDao,
    private val shoppingRepository: ShoppingRepository,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState = _uiState.asStateFlow()

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            combine(
                tokenDataStore.getUser().filterNotNull().flatMapLatest { user -> productDao.getAllProducts(user.id) },
                shoppingRepository.getShoppingItems()
            ) { products, shoppingItems ->
                val now = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.time
                
                val sevenDaysFromNow = Calendar.getInstance().apply { 
                    time = now
                    add(Calendar.DAY_OF_YEAR, 7) 
                }.time

                val lowStockList = products.filter { it.cantidad <= 3 }
                val criticalExpiryCount = products.count { 
                    isExpired(it.fechaVencimiento, now) || isNearExpiry(it.fechaVencimiento, now, sevenDaysFromNow)
                }
                
                val alerts = products.filter { it.cantidad <= 3 || isExpired(it.fechaVencimiento, now) || isNearExpiry(it.fechaVencimiento, now, sevenDaysFromNow) }
                    .sortedBy { it.fechaVencimiento }
                    .take(3)
                    .map { product ->
                        val isExpired = isExpired(product.fechaVencimiento, now)
                        ProductAlert(
                            name = product.nombre,
                            status = if (isExpired) "Caducó el ${product.fechaVencimiento}" else if (product.cantidad <= 3) "Solo quedan ${product.cantidad} unidades" else "Caduca pronto",
                            tag = if (isExpired || isNearExpiry(product.fechaVencimiento, now, sevenDaysFromNow)) "Caduca" else "Bajo",
                            isCritical = isExpired || product.cantidad <= 1
                        )
                    }

                val suggestedProduct = lowStockList.firstOrNull { prod -> 
                    shoppingItems.none { it.nombre.equals(prod.nombre, ignoreCase = true) }
                }

                val smartSuggestion = suggestedProduct?.let {
                    SmartSuggestion(
                        productName = it.nombre,
                        message = "Parece que consumes mucho ${it.nombre}. Agrégala a tu lista para aprovechar ofertas."
                    )
                }

                DashboardState(
                    totalProducts = products.size,
                    lowStockProducts = lowStockList.size,
                    nearExpiryProducts = criticalExpiryCount,
                    alerts = alerts,
                    suggestion = smartSuggestion,
                    isLoading = false
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    fun addSuggestedToShopping() {
        val suggestion = _uiState.value.suggestion ?: return
        viewModelScope.launch {
            shoppingRepository.addShoppingItem(
                ShoppingItem(
                    nombre = suggestion.productName,
                    categoria = "Sugerencia Inteligente",
                    cantidad = "1 u"
                )
            )
        }
    }

    private fun isNearExpiry(dateStr: String?, now: Date, limit: Date): Boolean {
        if (dateStr.isNullOrBlank()) return false
        return try {
            val date = dateFormatter.parse(dateStr)
            date != null && (date == now || (date.after(now) && date.before(limit)))
        } catch (e: Exception) { false }
    }

    private fun isExpired(dateStr: String?, now: Date): Boolean {
        if (dateStr.isNullOrBlank()) return false
        return try {
            val date = dateFormatter.parse(dateStr)
            date != null && date.before(now)
        } catch (e: Exception) { false }
    }
}
