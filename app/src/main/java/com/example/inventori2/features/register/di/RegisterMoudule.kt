package com.example.inventori2.features.register.di

import com.example.inventori2.core.di.AppContainer
import com.example.inventori2.features.register.domain.usecase.RegisterUseCase
import com.example.inventori2.features.register.presentation.viewmodels.RegisterViewModelFactory


class RegisterModule(
    private val appContainer: AppContainer
) {

    private fun provideRegisterUseCase(): RegisterUseCase {
        return RegisterUseCase(
            repository = appContainer.registerRepository
        )
    }

    fun provideRegisterViewModelFactory(): RegisterViewModelFactory {
        return RegisterViewModelFactory(
            registerUseCase = provideRegisterUseCase()
        )
    }
}
