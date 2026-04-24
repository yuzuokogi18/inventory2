package com.example.inventori2.features.inventory.product_create.data.datasources.models

import com.google.gson.annotations.SerializedName

data class ProductActionRequest(
    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("fecha_vencimiento")
    val fechaVencimiento: String,

    @SerializedName("categoria_id")
    val categoriaId: Int? = null,
    
    @SerializedName("imagen_uri")
    val imagenUri: String? = null // NUEVO
)
