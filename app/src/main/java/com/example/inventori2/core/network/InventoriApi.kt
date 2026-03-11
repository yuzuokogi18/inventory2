package com.example.inventori2.core.network

import com.example.inventori2.features.login.data.datasources.models.LoginRequest
import com.example.inventori2.features.login.data.datasources.models.LoginResponse
import com.example.inventori2.features.product_create.data.datasources.models.ProductActionRequest
import com.example.inventori2.features.product_create.data.datasources.models.ProductActionResponse
import com.example.inventori2.features.product_list.data.datasources.models.ProductResponse
import com.example.inventori2.features.register.data.datasources.models.RegisterRequest
import com.example.inventori2.features.register.data.datasources.models.RegisterResponse
import com.example.inventori2.features.product_detail.data.datasources.models.ProductDetailDTO
import com.example.inventori2.features.product_edit.data.datasources.models.ProductEditRequest
import retrofit2.http.*

interface InventoriApi {

    @POST("api/auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("api/auth/register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("api/productos")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body product: ProductActionRequest
    ): ProductActionResponse

    @GET("api/productos")
    suspend fun getAllProducts(
        @Header("Authorization") token: String
    ): List<ProductResponse>

    @GET("api/productos/{id}")
    suspend fun getProductById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): ProductDetailDTO

    @PUT("api/productos/{id}")
    suspend fun updateProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body product: ProductEditRequest
    ): ProductActionResponse

    // 🔥 DELETE PRODUCT (NUEVO)
    @DELETE("api/productos/{id}")
    suspend fun deleteProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): ProductActionResponse
}