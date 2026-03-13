package com.example.inventori2.features.profile.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.features.login.domain.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _loggedOut = MutableStateFlow(false)
    val loggedOut = _loggedOut.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            _user.value = tokenDataStore.getUser().firstOrNull()
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenDataStore.clearAll()
            _loggedOut.value = true
        }
    }
}
