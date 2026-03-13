package com.example.inventori2.features.product_create.data.repositories

import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.core.database.entities.ProductEntity
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.features.product_create.data.datasources.models.ProductActionRequest
import com.example.inventori2.features.product_create.domain.entities.ProductCreate
import com.example.inventori2.features.product_create.domain.repositories.CreateProductRepository
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDateTime
import javax.inject.Inject

class CreateProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val tokenDataStore: TokenDataStore
) : CreateProductRepository {

    override suspend fun createProduct(product: ProductActionRequest): ProductCreate {
        // Obtenemos el usuario actual del DataStore
        val user = tokenDataStore.getUser().firstOrNull() 
            ?: throw Exception("Usuario no autenticado")

        // Creamos la entidad para Room
        val productEntity = ProductEntity(
            nombre = product.nombre,
            cantidad = product.cantidad,
            fechaVencimiento = product.fechaVencimiento,
            categoriaId = product.categoriaId,
            usuarioId = user.id,
            createdAt = LocalDateTime.now().toString()
        )

        // Insertamos en SQLite
        productDao.insertProduct(productEntity)

        // Devolvemos el objeto de dominio (simulado ya que Room no devuelve el objeto completo tras insertar)
        return ProductCreate(
            id = 0, // El ID real lo asigna SQLite
            nombre = product.nombre,
            cantidad = product.cantidad,
            fecha_vencimiento = product.fechaVencimiento,
            categoriaId = product.categoriaId,
            usuarioId = user.id,
            createdAt = productEntity.createdAt
        )
    }
}