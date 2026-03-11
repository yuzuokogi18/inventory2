package com.example.inventori2.features.register.data.datasources.models


import com.google.gson.annotations.SerializedName


data class RegisterRequest(
    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)

