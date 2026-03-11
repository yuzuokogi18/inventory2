package com.example.inventori2.features.product_detail.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.features.product_detail.domain.usecases.GetProductByIdUseCase
import com.example.inventori2.features.product_detail.presentation.screens.ProductDetailUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUIState())
    val uiState = _uiState.asStateFlow()

    fun loadProduct(productId: Int) { // Renombrado de loadCourseDetail
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = getProductByIdUseCase(productId)
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { product ->
                        currentState.copy(
                            isLoading = false,
                            product = product // Usamos product
                        )
                    },
                    onFailure = { error ->
                        currentState.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                )
            }
        }
    }
}