package com.example.inventori2.features.compras.domain.usecases

import com.example.inventori2.features.compras.domain.entities.ShoppingItem
import com.example.inventori2.features.compras.domain.repositories.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingItemsUseCase @Inject constructor(
    private val repository: ShoppingRepository
) {
    operator fun invoke(): Flow<List<ShoppingItem>> = repository.getShoppingItems()
}
