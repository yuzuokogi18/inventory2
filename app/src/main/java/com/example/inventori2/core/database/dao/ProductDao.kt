package com.example.inventori2.core.database.dao

import androidx.room.*
import com.example.inventori2.core.database.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM productos WHERE usuarioId = :userId")
    fun getAllProducts(userId: Int): Flow<List<ProductEntity>>

    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("DELETE FROM productos WHERE id = :id")
    suspend fun deleteProductById(id: Int)
}