package com.example.inventori2.features.product_delete.di

import com.example.inventori2.core.di.AppContainer
import com.example.inventori2.features.product_delete.data.repositories.ProductDeleteRepositoryImpl
import com.example.inventori2.features.product_delete.domain.repositories.ProductDeleteRepository
import com.example.inventori2.features.product_delete.domain.usecases.DeleteProductUseCase
import com.example.inventori2.features.product_delete.presentation.viewmodels.ProductDeleteViewModelFactory

class ProductDeleteModule(
    private val appContainer: AppContainer
) {
    fun provideProductDeleteRepository(): ProductDeleteRepository {
        return ProductDeleteRepositoryImpl(
            api = appContainer.inventoriApi,
            tokenDataStore = appContainer.tokenDataStore
        )
    }

    fun provideDeleteProductUseCase(): DeleteProductUseCase {
        return DeleteProductUseCase(provideProductDeleteRepository())
    }

    fun provideProductDeleteViewModelFactory(): ProductDeleteViewModelFactory {
        return ProductDeleteViewModelFactory(provideDeleteProductUseCase())
    }
}