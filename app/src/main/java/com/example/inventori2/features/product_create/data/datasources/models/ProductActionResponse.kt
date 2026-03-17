package com.example.inventori2.features.product_create.data.datasources.models

import com.google.gson.annotations.SerializedName

data class ProductActionResponse(
    @SerializedName("message")
    val message: String?,

    @SerializedName("producto")
    val producto: ProductActionDTO?
)
data class ProductActionDTO(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("fecha_vencimiento")
    val fechaVencimiento: String,

    @SerializedName("categoria_id")
    val categoriaId: Int? = null,

    @SerializedName("usuario_id")
    val usuarioId: Int,

    @SerializedName("created_at")
    val createdAt: String?
)
