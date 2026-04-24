package com.example.inventori2.features.inventory.product_detail.data.repositories

import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.features.inventory.product_detail.domain.entities.ProductDetail
import com.example.inventori2.features.inventory.product_detail.domain.repositories.ProductDetailRepository

import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductDetailRepository {

    override suspend fun getProductById(id: Int): ProductDetail {
        val entity = productDao.getProductById(id)
            ?: throw Exception("Producto no encontrado")

        return ProductDetail(
            id = entity.id,
            nombre = entity.nombre,
            cantidad = entity.cantidad,
            fechaVencimiento = entity.fechaVencimiento,
            categoriaId = entity.categoriaId,
            usuarioId = entity.usuarioId,
            imagenUri = entity.imagenUri // SE AÑADIÓ ESTO
        )
    }
}
