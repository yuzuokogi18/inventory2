package com.example.inventori2.features.product_create.data.repositories

import android.util.Log
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.core.network.InventoriApi
import com.example.inventori2.features.product_create.data.datasources.mapper.toDomain
import com.example.inventori2.features.product_create.data.datasources.models.ProductActionRequest
import com.example.inventori2.features.product_create.domain.entities.ProductCreate
import com.example.inventori2.features.product_create.domain.repositories.CreateProductRepository
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class CreateProductRepositoryImpl(
    private val api: InventoriApi,
    private val tokenDataStore: TokenDataStore
) : CreateProductRepository {

    override suspend fun createProduct(
        product: ProductActionRequest
    ): ProductCreate {

        return try {
            // Obtener token
            val token = tokenDataStore.getToken().first()
            if (token.isNullOrBlank()) {
                throw Exception("Token vacío o no encontrado")
            }

            Log.d("TOKEN", token)

            // Llamada al API
            val response = api.createProduct(
                token = "Bearer $token",
                product = product
            )

            // Mapear DTO a dominio, asegurando createdAt
            response.producto?.toDomain()
                ?: throw Exception("Producto viene null desde el backend")

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("HTTP_400", errorBody ?: "Sin body")
            throw Exception(errorBody ?: "Error HTTP ${e.code()}")
        } catch (e: Exception) {
            Log.e("EXCEPTION", e.message ?: "Error desconocido")
            throw e
        }
    }
}