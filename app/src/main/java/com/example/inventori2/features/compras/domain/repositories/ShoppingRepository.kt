package com.example.inventori2.features.compras.domain.repositories

import com.example.inventori2.features.compras.domain.entities.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    fun getShoppingItems(): Flow<List<ShoppingItem>>
    suspend fun addShoppingItem(item: ShoppingItem): Result<Unit>
    suspend fun updateShoppingItem(item: ShoppingItem): Result<Unit>
    suspend fun deleteShoppingItem(id: Int): Result<Unit>
    suspend fun syncInventoryWithShopping(): Result<Unit>
    suspend fun importPurchasedToInventory(): Result<Unit> // Nuevo método
}
