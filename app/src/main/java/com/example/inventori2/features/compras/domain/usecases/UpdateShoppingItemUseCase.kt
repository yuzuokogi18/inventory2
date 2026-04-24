package com.example.inventori2.features.compras.domain.usecases

import com.example.inventori2.features.compras.domain.entities.ShoppingItem
import com.example.inventori2.features.compras.domain.repositories.ShoppingRepository
import javax.inject.Inject

class UpdateShoppingItemUseCase @Inject constructor(
    private val repository: ShoppingRepository
) {
    suspend operator fun invoke(item: ShoppingItem): Result<Unit> = repository.updateShoppingItem(item)
}
