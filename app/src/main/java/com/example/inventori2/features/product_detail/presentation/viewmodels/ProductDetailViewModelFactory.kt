package com.example.inventori2.features.product_detail.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inventori2.features.product_detail.domain.usecases.GetProductByIdUseCase

class ProductDetailViewModelFactory(
    private val getCourseByIdUseCase: GetProductByIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(getCourseByIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}