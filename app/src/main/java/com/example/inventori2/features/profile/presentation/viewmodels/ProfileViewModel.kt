package com.example.inventori2.features.profile.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.core.datastore.SettingsDataStore
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.features.login.domain.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _loggedOut = MutableStateFlow(false)
    val loggedOut = _loggedOut.asStateFlow()

    val isDarkMode = settingsDataStore.isDarkMode
        .map { it ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            _user.value = tokenDataStore.getUser().firstOrNull()
        }
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setDarkMode(enabled)
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenDataStore.clearAll()
            _loggedOut.value = true
        }
    }
}
