package com.example.inventori2.core.di

import android.content.Context
import com.example.inventori2.BuildConfig
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.core.network.InventoriApi
import com.example.inventori2.features.login.data.repositories.LoginRepositoryImpl
import com.example.inventori2.features.register.data.repositories.RegisterRepositoryImpl
import com.example.inventori2.features.product_create.data.repositories.CreateProductRepositoryImpl
import com.example.inventori2.features.product_create.domain.repositories.CreateProductRepository
import com.example.inventori2.features.product_list.data.repositories.ProductRepositoryImpl
import com.example.inventori2.features.product_list.domain.repositories.ProductRepository
import com.example.inventori2.features.product_detail.data.repositories.ProductDetailRepositoryImpl
import com.example.inventori2.features.product_detail.domain.repositories.ProductDetailRepository
import com.example.inventori2.features.product_detail.domain.usecases.GetProductByIdUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // He quitado el 'private' para que los módulos puedan acceder
    val tokenDataStore = TokenDataStore(context)

    // He quitado el 'private' para que los módulos puedan acceder
    val inventoriApi: InventoriApi by lazy {
        retrofit.create(InventoriApi::class.java)
    }

    val loginRepository by lazy {
        LoginRepositoryImpl(inventoriApi, tokenDataStore)
    }

    val registerRepository by lazy {
        RegisterRepositoryImpl(inventoriApi, tokenDataStore)
    }

    val createProductRepository: CreateProductRepository by lazy {
        CreateProductRepositoryImpl(inventoriApi, tokenDataStore)
    }

    val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl(inventoriApi, tokenDataStore)
    }

    val productDetailRepository: ProductDetailRepository by lazy {
        ProductDetailRepositoryImpl(inventoriApi, tokenDataStore)
    }

    val getProductByIdUseCase by lazy {
        GetProductByIdUseCase(productDetailRepository)
    }
}