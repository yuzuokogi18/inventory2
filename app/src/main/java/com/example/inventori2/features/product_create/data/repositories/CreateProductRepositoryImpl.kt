package com.example.inventori2.features.product_create.data.repositories

import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.core.database.entities.ProductEntity
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.features.product_create.data.datasources.models.ProductActionRequest
import com.example.inventori2.features.product_create.domain.entities.ProductCreate
import com.example.inventori2.features.product_create.domain.repositories.CreateProductRepository
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDateTime

class CreateProductRepositoryImpl(
    private val productDao: ProductDao,
    private val tokenDataStore: TokenDataStore
) : CreateProductRepository {

    override suspend fun createProduct(product: ProductActionRequest): ProductCreate {
        val user = tokenDataStore.getUser().firstOrNull() 
            ?: throw Exception("Usuario no autenticado")

        val productEntity = ProductEntity(
            nombre = product.nombre,
            cantidad = product.cantidad,
            fechaVencimiento = product.fechaVencimiento,
            categoriaId = product.categoriaId,
            usuarioId = user.id,
            createdAt = LocalDateTime.now().toString()
        )

        productDao.insertProduct(productEntity)

        return ProductCreate(
            id = 0, 
            nombre = product.nombre,
            cantidad = product.cantidad,
            fecha_vencimiento = product.fechaVencimiento,
            categoriaId = product.categoriaId,
            usuarioId = user.id,
            createdAt = productEntity.createdAt
        )
    }
}