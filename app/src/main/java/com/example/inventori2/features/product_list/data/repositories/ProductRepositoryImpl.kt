package com.example.inventori2.features.product_list.data.repositories

import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.core.network.InventoriApi
import com.example.inventori2.features.product_list.data.datasources.mapper.toDomain
import com.example.inventori2.features.product_list.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.first
import com.example.inventori2.features.product_list.domain.entities.Product
class ProductRepositoryImpl(
    private val api: InventoriApi,
    private val tokenDataStore: TokenDataStore
) : ProductRepository {

    override suspend fun getAllProducts(): List<Product> {
        val token = tokenDataStore.getToken().first()
        return api.getAllProducts("Bearer $token").map { it.toDomain() }
    }
}