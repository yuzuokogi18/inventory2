package com.example.inventori2.features.product_delete.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inventori2.features.product_delete.domain.usecases.DeleteProductUseCase

class ProductDeleteViewModelFactory(
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDeleteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDeleteViewModel(deleteProductUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}