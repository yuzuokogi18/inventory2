package com.example.inventori2.features.compras.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.core.hardware.domain.ExportadorManager
import com.example.inventori2.features.compras.domain.entities.ShoppingItem
import com.example.inventori2.features.compras.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ShoppingUIState(
    val items: List<ShoppingItem> = emptyList(),
    val suggestions: List<ShoppingItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val getShoppingItemsUseCase: GetShoppingItemsUseCase,
    private val addShoppingItemUseCase: AddShoppingItemUseCase,
    private val updateShoppingItemUseCase: UpdateShoppingItemUseCase,
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase,
    private val syncInventoryUseCase: SyncInventoryUseCase,
    private val importToInventoryUseCase: ImportToInventoryUseCase,
    private val exportadorManager: ExportadorManager // Inyectado para hardware
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingUIState())
    val uiState = _uiState.asStateFlow()

    init {
        loadItems()
        syncWithInventory()
    }

    private fun loadItems() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getShoppingItemsUseCase().collect { allItems ->
                val (rawSuggestions, listItems) = allItems.partition { it.categoria == "Sugerencia" }
                
                val uniqueSuggestions = rawSuggestions
                    .distinctBy { it.nombre.lowercase().trim() }
                    .ifEmpty { getDefaultSuggestions() }

                _uiState.update { it.copy(
                    items = listItems,
                    suggestions = uniqueSuggestions,
                    isLoading = false
                ) }
            }
        }
    }

    fun syncWithInventory() {
        viewModelScope.launch {
            syncInventoryUseCase()
        }
    }

    fun addItem(nombre: String, cantidad: String = "1 u") {
        if (nombre.isBlank()) return
        viewModelScope.launch {
            addShoppingItemUseCase(ShoppingItem(nombre = nombre, categoria = "Varios", cantidad = cantidad))
        }
    }

    fun toggleComplete(item: ShoppingItem) {
        viewModelScope.launch {
            updateShoppingItemUseCase(item.copy(isCompleted = !item.isCompleted))
        }
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch {
            deleteShoppingItemUseCase(id)
        }
    }

    fun importToInventory() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = importToInventoryUseCase()
            result.fold(
                onSuccess = { _uiState.update { it.copy(isLoading = false) } },
                onFailure = { error -> _uiState.update { it.copy(isLoading = false, error = error.message) } }
            )
        }
    }

    fun descargarLista(items: List<String>) {
        if (items.isEmpty()) return
        // Llamada al hardware de almacenamiento
        exportadorManager.exportarListaAArchivo("Lista de Compras", items)
    }

    private fun getDefaultSuggestions() = listOf(
        ShoppingItem(nombre = "Arroz Integral", categoria = "Sugerencia", cantidad = "Stock bajo"),
        ShoppingItem(nombre = "Aceite Oliva", categoria = "Sugerencia", cantidad = "Consumo frecuente"),
        ShoppingItem(nombre = "Yogurt Griego", categoria = "Sugerencia", cantidad = "Próximo a vencer")
    )
}
