package com.example.inventori2.core.di

import com.example.inventori2.features.login.domain.repositories.LoginRepository
import com.example.inventori2.features.login.domain.usecases.LoginUseCase
import com.example.inventori2.features.register.domain.repositories.RegisterRepository
import com.example.inventori2.features.register.domain.usecases.RegisterUseCase
import com.example.inventori2.features.product_create.domain.repositories.CreateProductRepository
import com.example.inventori2.features.product_create.domain.usecases.CreateProductUseCase
import com.example.inventori2.features.product_list.domain.repositories.ProductRepository
import com.example.inventori2.features.product_list.domain.usecases.GetAllProductUseCase
import com.example.inventori2.features.product_detail.domain.repositories.ProductDetailRepository
import com.example.inventori2.features.product_detail.domain.usecases.GetProductByIdUseCase
import com.example.inventori2.features.product_edit.domain.repositories.ProductEditRepository
import com.example.inventori2.features.product_edit.domain.usecases.UpdateProductUseCase
import com.example.inventori2.features.product_delete.domain.repositories.ProductDeleteRepository
import com.example.inventori2.features.product_delete.domain.usecases.DeleteProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: LoginRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: RegisterRepository): RegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCreateProductUseCase(repository: CreateProductRepository): CreateProductUseCase {
        return CreateProductUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllProductUseCase(repository: ProductRepository): GetAllProductUseCase {
        return GetAllProductUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetProductByIdUseCase(repository: ProductDetailRepository): GetProductByIdUseCase {
        return GetProductByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateProductUseCase(repository: ProductEditRepository): UpdateProductUseCase {
        return UpdateProductUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteProductUseCase(repository: ProductDeleteRepository): DeleteProductUseCase {
        return DeleteProductUseCase(repository)
    }
}
