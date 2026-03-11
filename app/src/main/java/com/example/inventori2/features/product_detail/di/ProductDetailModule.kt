package com.example.inventori2.features.product_detail.di

import com.example.inventori2.core.di.AppContainer
import com.example.inventori2.features.product_detail.presentation.viewmodels.ProductDetailViewModelFactory

class ProductDetailModule(
    private val appContainer: AppContainer
) {
    fun provideProductDetailViewModelFactory(): ProductDetailViewModelFactory {
        return ProductDetailViewModelFactory(appContainer.getProductByIdUseCase)
    }
}