package com.example.inventori2.features.product_edit.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inventori2.features.product_detail.domain.usecases.GetProductByIdUseCase
import com.example.inventori2.features.product_edit.domain.usecases.UpdateProductUseCase

class ProductEditViewModelFactory(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductEditViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductEditViewModel(getProductByIdUseCase, updateProductUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}