package com.example.inventori2.core.di.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventori2.features.login.presentation.screens.LoginScreen
import com.example.inventori2.features.register.presentation.screens.RegisterScreen
import com.example.inventori2.features.inventory.product_create.presentation.screens.CreateProductScreen
import com.example.inventori2.features.inventory.product_detail.presentation.screens.ProductDetailScreen
import com.example.inventori2.features.inventory.product_edit.presentation.screens.ProductEditScreen
import com.example.inventori2.features.inventory.product_list.presentation.screens.ProductsScreen
import com.example.inventori2.features.dashboard.presentation.screens.DashboardScreen
import com.example.inventori2.features.profile.presentation.screens.ProfileScreen
import com.example.inventori2.features.compras.presentation.screens.ShoppingScreen
import com.example.inventori2.features.estadisticas.presentation.screens.StatsScreen
import com.example.inventori2.features.notification.presentation.screens.NotificationScreen

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.Login.route
    ) {

        composable(AppRoutes.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(AppRoutes.Register.route) },
                onLoginSuccess = {
                    navController.navigate(AppRoutes.Dashboard.route) {
                        popUpTo(AppRoutes.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(AppRoutes.Login.route) {
                        popUpTo(AppRoutes.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.Dashboard.route) {
            DashboardScreen(
                onNavigateToList = { navController.navigate(AppRoutes.ProductList.route) },
                onNavigateToProfile = { navController.navigate(AppRoutes.Profile.route) },
                onNavigateToCreate = { navController.navigate(AppRoutes.CreateProduct.route) },
                onNavigateToShopping = { navController.navigate(AppRoutes.Shopping.route) },
                onNavigateToStats = { navController.navigate(AppRoutes.Stats.route) },
                onNavigateToNotifications = { navController.navigate(AppRoutes.Notifications.route) }
            )
        }

        composable(AppRoutes.Shopping.route) {
            ShoppingScreen(
                onNavigateToInicio = { navController.navigate(AppRoutes.Dashboard.route) },
                onNavigateToInventory = { navController.navigate(AppRoutes.ProductList.route) },
                onNavigateToProfile = { navController.navigate(AppRoutes.Profile.route) }
            )
        }

        composable(AppRoutes.Stats.route) {
            StatsScreen(
                onNavigateToInicio = { navController.navigate(AppRoutes.Dashboard.route) },
                onNavigateToInventory = { navController.navigate(AppRoutes.ProductList.route) },
                onNavigateToShopping = { navController.navigate(AppRoutes.Shopping.route) },
                onNavigateToProfile = { navController.navigate(AppRoutes.Profile.route) }
            )
        }

        composable(AppRoutes.Notifications.route) {
            NotificationScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.Profile.route) {
            ProfileScreen(
                onLogout = {
                    navController.navigate(AppRoutes.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.ProductList.route) {
            ProductsScreen(
                onNavigateToInicio = { navController.navigate(AppRoutes.Dashboard.route) },
                onNavigateToShopping = { navController.navigate(AppRoutes.Shopping.route) },
                onNavigateToProfile = { navController.navigate(AppRoutes.Profile.route) },
                onCreateProductClick = { navController.navigate(AppRoutes.CreateProduct.route) },
                onEditClick = { id -> navController.navigate(AppRoutes.EditProduct.createRoute(id)) },
                onViewClick = { id -> navController.navigate(AppRoutes.ProductDetail.createRoute(id)) }
            )
        }

        composable(AppRoutes.CreateProduct.route) {
            CreateProductScreen(onBackClick = { navController.popBackStack() }, onSuccess = { navController.popBackStack() })
        }

        composable(
            route = AppRoutes.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(productId = productId, onBackClick = { navController.popBackStack() })
        }

        composable(
            route = AppRoutes.EditProduct.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductEditScreen(productId = productId, onBackClick = { navController.popBackStack() }, onSuccess = { navController.popBackStack() })
        }
    }
}
