package com.example.inventori2.features.product_edit.di

import com.example.inventori2.core.di.AppContainer
import com.example.inventori2.features.product_edit.data.repositories.ProductEditRepositoryImpl
import com.example.inventori2.features.product_edit.domain.repositories.ProductEditRepository
import com.example.inventori2.features.product_edit.domain.usecases.UpdateProductUseCase
import com.example.inventori2.features.product_edit.presentation.viewmodels.ProductEditViewModelFactory

class ProductEditModule(
    private val appContainer: AppContainer
) {
    fun provideProductEditRepository(): ProductEditRepository {
        return ProductEditRepositoryImpl(
            api = appContainer.inventoriApi,
            tokenDataStore = appContainer.tokenDataStore
        )
    }

    fun provideUpdateProductUseCase(): UpdateProductUseCase {
        return UpdateProductUseCase(provideProductEditRepository())
    }

    fun provideProductEditViewModelFactory(): ProductEditViewModelFactory {
        return ProductEditViewModelFactory(
            getProductByIdUseCase = appContainer.getProductByIdUseCase,
            updateProductUseCase = provideUpdateProductUseCase()
        )
    }
}