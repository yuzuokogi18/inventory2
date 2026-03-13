package com.example.inventori2.features.product_delete.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.features.product_delete.domain.usecases.DeleteProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDeleteViewModel @Inject constructor(
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _isDeleted = MutableStateFlow(false)
    val isDeleted = _isDeleted.asStateFlow()

    fun deleteProduct(productId: Int) {
        _isLoading.value = true
        _error.value = null
        
        viewModelScope.launch {
            val result = deleteProductUseCase(productId)
            result.fold(
                onSuccess = {
                    _isLoading.value = false
                    _isDeleted.value = true
                },
                onFailure = { error ->
                    _isLoading.value = false
                    _error.value = error.message ?: "Error al eliminar el producto"
                }
            )
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun resetDeleteStatus() {
        _isDeleted.value = false
    }
}