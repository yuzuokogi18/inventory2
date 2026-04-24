package com.example.inventori2.core.di.navigation

sealed class AppRoutes(val route: String) {
    object Login : AppRoutes("login")
    object Register : AppRoutes("register")
    object Dashboard : AppRoutes("dashboard")
    object Profile : AppRoutes("profile")
    object Shopping : AppRoutes("shopping")
    object Stats : AppRoutes("stats")
    object Notifications : AppRoutes("notifications")

    object ProductList : AppRoutes("products/list")
    object CreateProduct : AppRoutes("products/create")
    object ProductDetail : AppRoutes("products/detail/{productId}") {
        fun createRoute(productId: Int) = "products/detail/$productId"
    }
    object EditProduct : AppRoutes("products/edit/{productId}") {
        fun createRoute(productId: Int) = "products/edit/$productId"
    }
}
