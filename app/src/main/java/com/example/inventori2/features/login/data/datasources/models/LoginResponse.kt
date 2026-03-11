package com.example.inventori2.features.login.data.datasources.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable
import com.example.inventori2.features.login.domain.entities.User

data class LoginResponse(
    @SerializedName("token")
    val token: String,

    @SerializedName("usuario")
    val user: LoginDTO
)


data class LoginDTO(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("email")
    val email: String,


    @SerializedName("created_at") // <-- coincidencia con JSON
    val createdAt: String,

    @SerializedName("updated_at") // <-- coincidencia con JSON
    val updatedAt: String
) : Serializable
