package com.example.inventori2.features.notification.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.features.notification.domain.entities.Notification
import com.example.inventori2.features.notification.domain.repositories.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotificationUiState(
    val notifications: List<Notification> = emptyList(),
    val unreadCount: Int = 0,
    val isLoading: Boolean = false
)

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        _uiState.update { it.copy(isLoading = true) }
        
        viewModelScope.launch {
            // Combinamos la lista de notificaciones y el conteo de no leídos
            combine(
                repository.getNotifications(),
                repository.getUnreadCount()
            ) { notifications, unreadCount ->
                NotificationUiState(
                    notifications = notifications,
                    unreadCount = unreadCount,
                    isLoading = false
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    fun markAsRead(id: Int) {
        viewModelScope.launch {
            repository.markAsRead(id)
        }
    }

    fun markAllAsRead() {
        viewModelScope.launch {
            repository.markAllAsRead()
        }
    }

    fun deleteNotification(id: Int) {
        viewModelScope.launch {
            repository.deleteNotification(id)
        }
    }
}
