package com.example.inventori2.features.product_list.data.datasources.models

import com.google.gson.annotations.SerializedName


data class ProductResponse(

    @SerializedName("id")
    val id: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("fecha_vencimiento")
    val fechaVencimiento: String,

    @SerializedName("categoria_id")
    val categoriaId: Int?,

    @SerializedName("usuario_id")
    val usuarioId: Int,

    @SerializedName("created_at")
    val createdAt: String?
)