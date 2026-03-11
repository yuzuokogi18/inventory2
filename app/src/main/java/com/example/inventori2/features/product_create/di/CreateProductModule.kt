package com.example.inventori2.features.product_create.di

import com.example.inventori2.core.di.AppContainer
import com.example.inventori2.features.product_create.domain.usecases.CreateProductUseCase
import com.example.inventori2.features.product_create.presentation.viewmodels.CreateProductViewModelFactory

class CreateProductModule(
    private val appContainer: AppContainer
) {
    private fun provideCreateProductUseCase(): CreateProductUseCase {
        return CreateProductUseCase(repository = appContainer.createProductRepository)
    }

    fun provideCreateProductViewModelFactory(): CreateProductViewModelFactory {
        return CreateProductViewModelFactory(
            createProductUseCase = provideCreateProductUseCase()
        )
    }
}