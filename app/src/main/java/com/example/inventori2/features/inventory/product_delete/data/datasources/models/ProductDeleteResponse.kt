package com.example.inventori2.features.inventory.product_delete.data.datasources.models

import com.google.gson.annotations.SerializedName

data class ProductDeleteResponse(
    @SerializedName("message")
    val message: String?
)