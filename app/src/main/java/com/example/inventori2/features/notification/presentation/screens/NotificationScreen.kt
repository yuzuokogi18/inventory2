package com.example.inventori2.features.notification.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventori2.core.ui.components.MainScaffold
import com.example.inventori2.features.notification.domain.entities.Notification
import com.example.inventori2.features.notification.presentation.viewmodels.NotificationViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NotificationScreen(
    onBackClick: () -> Unit,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScaffold(
        topAppBar = {
            TopAppBar(
                title = { Text("Notificaciones", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.markAllAsRead() }) {
                        Icon(Icons.Default.DeleteSweep, contentDescription = "Limpiar todo", tint = Color.Gray)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            if (uiState.notifications.isEmpty()) {
                EmptyNotifications()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.notifications) { notification ->
                        NotificationItem(
                            notification = notification,
                            onMarkAsRead = { viewModel.markAsRead(notification.id) },
                            onDelete = { viewModel.deleteNotification(notification.id) }
                        )
                    }
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFF22C55E))
            }
        }
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
    onMarkAsRead: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMarkAsRead() },
        shape = RoundedCornerShape(16.dp),
        color = if (notification.isRead) Color(0xFFF9FAFB) else Color(0xFFF0FDF4),
        border = androidx.compose.foundation.BorderStroke(
            1.dp, 
            if (notification.isRead) Color(0xFFF3F4F6) else Color(0xFFDCFCE7)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (notification.isRead) Color.LightGray.copy(alpha = 0.2f) else Color(0xFF22C55E).copy(alpha = 0.1f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = if (notification.isRead) Color.Gray else Color(0xFF22C55E),
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = notification.title,
                    fontWeight = if (notification.isRead) FontWeight.Medium else FontWeight.Bold,
                    fontSize = 15.sp,
                    color = if (notification.isRead) Color.DarkGray else Color.Black
                )
                Text(
                    text = notification.message,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = formatTimestamp(notification.timestamp),
                    fontSize = 11.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            if (!notification.isRead) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color(0xFF22C55E), CircleShape)
                )
            }
        }
    }
}

@Composable
fun EmptyNotifications() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Outlined.Notifications,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("No tienes notificaciones", color = Color.Gray, fontWeight = FontWeight.Medium)
        Text("Te avisaremos cuando algo importante ocurra", color = Color.LightGray, fontSize = 12.sp)
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: @Composable () -> Unit,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = title,
        navigationIcon = navigationIcon ?: {},
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White
        )
    )
}
