package com.example.inventori2.features.login.di

import com.example.inventori2.core.di.AppContainer
import com.example.inventori2.features.login.domain.usecases.LoginUseCase
import com.example.inventori2.features.login.presentation.viewmodels.LoginViewModelFactory

class LoginModule(
    private val appContainer: AppContainer
) {

    private fun provideLoginUseCase(): LoginUseCase {
        return LoginUseCase(
            repository = appContainer.loginRepository
        )
    }

    fun provideLoginViewModelFactory(): LoginViewModelFactory {
        return LoginViewModelFactory(
            loginUseCase = provideLoginUseCase()
        )
    }
}
