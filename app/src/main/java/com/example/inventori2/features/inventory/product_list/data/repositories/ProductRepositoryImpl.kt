package com.example.inventori2.features.inventory.product_list.data.repositories

import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.features.inventory.product_list.domain.entities.Product
import com.example.inventori2.features.inventory.product_list.domain.repositories.ProductRepository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull


class ProductRepositoryImpl(
    private val productDao: ProductDao,
    private val tokenDataStore: TokenDataStore
) : ProductRepository {

    override suspend fun getAllProducts(): List<Product> {
        val user = tokenDataStore.getUser().firstOrNull() ?: return emptyList()
        val entities = productDao.getAllProducts(user.id).first()

        return entities.map { entity ->
            Product(
                id = entity.id,
                nombre = entity.nombre,
                cantidad = entity.cantidad,
                fechaVencimiento = entity.fechaVencimiento,
                categoriaId = entity.categoriaId,
                usuarioId = entity.usuarioId,
                createdAt = entity.createdAt,
                imagenUri = entity.imagenUri // SE AÑADIÓ ESTO
            )
        }
    }
}
