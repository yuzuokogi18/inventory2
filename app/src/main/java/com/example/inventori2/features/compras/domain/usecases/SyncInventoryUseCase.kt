package com.example.inventori2.features.compras.domain.usecases

import com.example.inventori2.features.compras.domain.repositories.ShoppingRepository
import javax.inject.Inject

class SyncInventoryUseCase @Inject constructor(
    private val repository: ShoppingRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.syncInventoryWithShopping()
}
