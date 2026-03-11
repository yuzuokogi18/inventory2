package com.example.inventori2.core.di.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventori2.features.login.presentation.screens.LoginScreen
import com.example.inventori2.features.register.presentation.screens.RegisterScreen
import com.example.inventori2.features.product_create.presentation.screens.CreateProductScreen
import com.example.inventori2.features.product_detail.presentation.screens.ProductDetailScreen
import com.example.inventori2.features.product_edit.presentation.screens.ProductEditScreen
import com.example.inventori2.features.product_list.presentation.screens.ProductsScreen

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.Login.route
    ) {
        // 🔥 LOGIN
        composable(AppRoutes.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(AppRoutes.Register.route) },
                onLoginSuccess = {
                    navController.navigate(AppRoutes.ProductList.route) {
                        popUpTo(AppRoutes.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // 🔥 REGISTER
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

        // 🔥 PRODUCT LIST
        composable(AppRoutes.ProductList.route) {
            ProductsScreen(
                onCreateProductClick = { navController.navigate(AppRoutes.CreateProduct.route) },
                onEditClick = { id -> navController.navigate(AppRoutes.EditProduct.createRoute(id)) },
                onViewClick = { id -> navController.navigate(AppRoutes.ProductDetail.createRoute(id)) }
            )
        }

        // 🔥 CREATE PRODUCT
        composable(AppRoutes.CreateProduct.route) {
            CreateProductScreen(
                onBackClick = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate(AppRoutes.ProductList.route) {
                        popUpTo(AppRoutes.ProductList.route) { inclusive = true }
                    }
                }
            )
        }

        // 🔥 PRODUCT DETAIL
        composable(
            route = AppRoutes.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() }
            )
        }

        // 🔥 PRODUCT EDIT
        composable(
            route = AppRoutes.EditProduct.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductEditScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() }
            )
        }
    }
}
