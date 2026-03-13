package com.example.inventori2.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.inventori2.core.database.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM usuarios WHERE email = :email AND contrasena = :password LIMIT 1")
    suspend fun login(email: String, password: String): UserEntity?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun register(user: UserEntity): Long
}
