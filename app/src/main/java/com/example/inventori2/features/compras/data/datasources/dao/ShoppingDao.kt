package com.example.inventori2.features.compras.data.datasources.dao

import androidx.room.*
import com.example.inventori2.features.compras.data.datasources.models.ShoppingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM shopping_items WHERE usuarioId = :userId")
    fun getShoppingItems(userId: Int): Flow<List<ShoppingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(item: ShoppingEntity)

    @Update
    suspend fun updateShoppingItem(item: ShoppingEntity)

    @Delete
    suspend fun deleteShoppingItem(item: ShoppingEntity)

    @Query("DELETE FROM shopping_items WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM shopping_items WHERE usuarioId = :userId")
    suspend fun clearAll(userId: Int)
}
