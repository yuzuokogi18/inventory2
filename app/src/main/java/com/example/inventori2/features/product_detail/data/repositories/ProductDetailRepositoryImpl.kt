package com.example.inventori2.features.product_detail.data.repositories



import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.core.network.InventoriApi
import com.example.inventori2.features.product_detail.data.datasources.mapper.toDomain
import com.example.inventori2.features.product_detail.domain.entities.ProductDetail
import com.example.inventori2.features.product_detail.domain.repositories.ProductDetailRepository
import kotlinx.coroutines.flow.first

class ProductDetailRepositoryImpl(
    private val api: InventoriApi,
    private val tokenDataStore: TokenDataStore
) : ProductDetailRepository {

    override suspend fun getProductById(id: Int): ProductDetail {
        val token = tokenDataStore.getToken().first()
        return api.getProductById("Bearer $token", id).toDomain()
    }
}