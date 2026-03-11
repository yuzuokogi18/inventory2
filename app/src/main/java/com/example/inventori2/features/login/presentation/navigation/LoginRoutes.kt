package com.example.inventori2.features.login.presentation.navigation

sealed class LoginRoutes(val route: String) {
    object Login : LoginRoutes("login")
    object Register : LoginRoutes("register")
}
