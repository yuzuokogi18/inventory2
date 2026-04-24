package com.example.inventori2.features.compras.data.repositories

import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.core.database.entities.ProductEntity
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.features.compras.data.datasources.mapper.toData
import com.example.inventori2.features.compras.data.datasources.mapper.toDomain
import com.example.inventori2.features.compras.domain.entities.ShoppingItem
import com.example.inventori2.features.compras.domain.repositories.ShoppingRepository
import com.example.inventori2.features.compras.data.datasources.dao.ShoppingDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val productDao: ProductDao,
    private val tokenDataStore: TokenDataStore
) : ShoppingRepository {

    override fun getShoppingItems(): Flow<List<ShoppingItem>> {
        return tokenDataStore.getUser().flatMapLatest { user ->
            shoppingDao.getShoppingItems(user?.id ?: 0).map { entities ->
                entities.map { it.toDomain() }
            }
        }
    }

    override suspend fun addShoppingItem(item: ShoppingItem): Result<Unit> {
        return try {
            val user = tokenDataStore.getUser().firstOrNull()
            if (user != null) {
                shoppingDao.insertShoppingItem(item.toData(user.id))
                Result.success(Unit)
            } else {
                Result.failure(Exception("Usuario no autenticado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateShoppingItem(item: ShoppingItem): Result<Unit> {
        return try {
            val user = tokenDataStore.getUser().firstOrNull()
            if (user != null) {
                shoppingDao.updateShoppingItem(item.toData(user.id))
                Result.success(Unit)
            } else {
                Result.failure(Exception("Usuario no autenticado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteShoppingItem(id: Int): Result<Unit> {
        return try {
            shoppingDao.deleteById(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun syncInventoryWithShopping(): Result<Unit> {
        return try {
            val user = tokenDataStore.getUser().firstOrNull() ?: return Result.failure(Exception("No user"))
            val products = productDao.getAllProducts(user.id).firstOrNull() ?: emptyList()
            
            products.filter { it.cantidad <= 3 }.forEach { product ->
                shoppingDao.insertShoppingItem(
                    ShoppingItem(
                        nombre = product.nombre,
                        categoria = "Sugerencia",
                        cantidad = "1 u",
                        isCompleted = false
                    ).toData(user.id)
                )
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun importPurchasedToInventory(): Result<Unit> {
        return try {
            val user = tokenDataStore.getUser().firstOrNull() ?: return Result.failure(Exception("Usuario no identificado"))
            val userId = user.id
            
            // 1. Obtener items completados
            val allEntities = shoppingDao.getShoppingItems(userId).first()
            val completedItems = allEntities.filter { it.isCompleted }
            
            if (completedItems.isEmpty()) return Result.success(Unit)

            // 2. Procesar cada item hacia el inventario
            val inventory = productDao.getAllProducts(userId).first()
            
            completedItems.forEach { item ->
                val existingProduct = inventory.find { it.nombre.equals(item.nombre, ignoreCase = true) }
                
                // Extraer número de la cantidad (ej: "2 u" -> 2)
                val addedQuantity = item.cantidad.filter { it.isDigit() }.toIntOrNull() ?: 1
                
                if (existingProduct != null) {
                    // Actualizar producto existente
                    productDao.updateProduct(
                        existingProduct.copy(cantidad = existingProduct.cantidad + addedQuantity)
                    )
                } else {
                    // Crear producto nuevo si no existe
                    productDao.insertProduct(
                        ProductEntity(
                            nombre = item.nombre,
                            cantidad = addedQuantity,
                            fechaVencimiento = "", // Se deja vacío para que el usuario edite luego
                            categoriaId = 0,
                            usuarioId = userId
                        )
                    )
                }
                
                // 3. Borrar de la lista de compras
                shoppingDao.deleteShoppingItem(item)
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
