package com.example.inventori2.features.compras.data.datasources.mapper

import com.example.inventori2.features.compras.data.datasources.models.ShoppingEntity
import com.example.inventori2.features.compras.domain.entities.ShoppingItem

fun ShoppingEntity.toDomain(): ShoppingItem {
    return ShoppingItem(
        id = id,
        nombre = nombre,
        categoria = categoria,
        cantidad = cantidad,
        isCompleted = isCompleted
    )
}

fun ShoppingItem.toData(usuarioId: Int): ShoppingEntity {
    return ShoppingEntity(
        id = id,
        nombre = nombre,
        categoria = categoria,
        cantidad = cantidad,
        isCompleted = isCompleted,
        usuarioId = usuarioId
    )
}
