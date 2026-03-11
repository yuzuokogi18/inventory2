package com.example.inventori2.features.product_list.di

import com.example.inventori2.core.di.AppContainer
import com.example.inventori2.features.product_list.domain.usecases.GetAllProductUseCase
import com.example.inventori2.features.product_list.presentation.viewmodels.ProductViewModelFactory

class ProductModule(
    private val appContainer: AppContainer
) {

    private fun provideGetAllProductsUseCase(): GetAllProductUseCase {
        return GetAllProductUseCase(
            repository = appContainer.productRepository
        )
    }

    fun provideProductViewModelFactory(): ProductViewModelFactory {
        return ProductViewModelFactory(
            getAllProductUseCase = provideGetAllProductsUseCase()
        )
    }
}