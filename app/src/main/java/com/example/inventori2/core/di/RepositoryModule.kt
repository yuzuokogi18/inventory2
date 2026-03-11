package com.example.inventori2.core.di

import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.core.network.InventoriApi
import com.example.inventori2.features.login.data.repositories.LoginRepositoryImpl
import com.example.inventori2.features.login.domain.repositories.LoginRepository
import com.example.inventori2.features.register.data.repositories.RegisterRepositoryImpl
import com.example.inventori2.features.register.domain.repositories.RegisterRepository
import com.example.inventori2.features.product_create.data.repositories.CreateProductRepositoryImpl
import com.example.inventori2.features.product_create.domain.repositories.CreateProductRepository
import com.example.inventori2.features.product_list.data.repositories.ProductRepositoryImpl
import com.example.inventori2.features.product_list.domain.repositories.ProductRepository
import com.example.inventori2.features.product_detail.data.repositories.ProductDetailRepositoryImpl
import com.example.inventori2.features.product_detail.domain.repositories.ProductDetailRepository
import com.example.inventori2.features.product_edit.data.repositories.ProductEditRepositoryImpl
import com.example.inventori2.features.product_edit.domain.repositories.ProductEditRepository
import com.example.inventori2.features.product_delete.data.repositories.ProductDeleteRepositoryImpl
import com.example.inventori2.features.product_delete.domain.repositories.ProductDeleteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(api: InventoriApi, ds: TokenDataStore): LoginRepository {
        return LoginRepositoryImpl(api, ds)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(api: InventoriApi, ds: TokenDataStore): RegisterRepository {
        return RegisterRepositoryImpl(api, ds)
    }

    @Provides
    @Singleton
    fun provideCreateProductRepository(api: InventoriApi, ds: TokenDataStore): CreateProductRepository {
        return CreateProductRepositoryImpl(api, ds)
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: InventoriApi, ds: TokenDataStore): ProductRepository {
        return ProductRepositoryImpl(api, ds)
    }

    @Provides
    @Singleton
    fun provideProductDetailRepository(api: InventoriApi, ds: TokenDataStore): ProductDetailRepository {
        return ProductDetailRepositoryImpl(api, ds)
    }

    @Provides
    @Singleton
    fun provideProductEditRepository(api: InventoriApi, ds: TokenDataStore): ProductEditRepository {
        return ProductEditRepositoryImpl(api, ds)
    }

    @Provides
    @Singleton
    fun provideProductDeleteRepository(api: InventoriApi, ds: TokenDataStore): ProductDeleteRepository {
        return ProductDeleteRepositoryImpl(api, ds)
    }
}
