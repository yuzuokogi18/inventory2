package com.example.inventori2.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventori2.core.di.navigation.AppRoutes
import com.example.inventori2.ui.theme.PrimaryGreen
import com.example.inventori2.ui.theme.SurfaceColor
import com.example.inventori2.ui.theme.TextSecondary

@Composable
fun MainScaffold(
    topAppBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    bottomAppBar: @Composable (() -> Unit)? = null,
    currentRoute: String? = null,
    onNavigate: ((String) -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = topAppBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        bottomBar = {
            if (bottomAppBar != null) {
                bottomAppBar()
            } else if (currentRoute != null && onNavigate != null && shouldShowBottomBar(currentRoute)) {
                BottomNavigationBar(currentRoute, onNavigate)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            content(paddingValues)
        }
    }
}

@Composable
fun BottomNavigationBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar(
        containerColor = SurfaceColor,
        tonalElevation = 8.dp,
        modifier = Modifier.height(80.dp)
    ) {
        val items = listOf(
            BottomNavItem("Inicio", AppRoutes.Dashboard.route, Icons.Default.Home, Icons.Outlined.Home),
            BottomNavItem("Inventario", AppRoutes.ProductList.route, Icons.Default.Inventory2, Icons.Outlined.Inventory2),
            BottomNavItem("Alertas", AppRoutes.Alerts.route, Icons.Default.Notifications, Icons.Outlined.Notifications),
            BottomNavItem("Compras", AppRoutes.ShoppingList.route, Icons.Default.ShoppingCart, Icons.Outlined.ShoppingCart),
            BottomNavItem("Perfil", AppRoutes.Profile.route, Icons.Default.Person, Icons.Outlined.Person)
        )

        items.forEach { item ->
            val selected = currentRoute?.startsWith(item.route) == true
            NavigationBarItem(
                selected = selected,
                onClick = { if (!selected) onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 10.sp,
                        color = if (selected) PrimaryGreen else TextSecondary
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryGreen,
                    unselectedIconColor = TextSecondary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

private fun shouldShowBottomBar(route: String?): Boolean {
    return route != null && 
           route != AppRoutes.Login.route && 
           route != AppRoutes.Register.route
}

data class BottomNavItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
