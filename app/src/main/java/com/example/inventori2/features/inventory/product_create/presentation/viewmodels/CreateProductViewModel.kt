package com.example.inventori2.features.inventory.product_create.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.core.hardware.data.ImageStorageManager
import com.example.inventori2.core.hardware.domain.CamaraManager
import com.example.inventori2.core.notification.NotificationHelper
import com.example.inventori2.features.inventory.product_create.data.datasources.models.ProductActionRequest
import com.example.inventori2.features.inventory.product_create.domain.usecases.CreateProductUseCase
import com.example.inventori2.features.inventory.product_create.presentation.screens.CreateProductUIState
import com.example.inventori2.features.notification.data.datasources.dao.NotificationDao
import com.example.inventori2.features.notification.data.datasources.models.NotificationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase,
    private val camaraManager: CamaraManager,
    private val imageStorageManager: ImageStorageManager, // Inyectamos el grabador
    private val notificationHelper: NotificationHelper,
    private val notificationDao: NotificationDao,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateProductUIState())
    val uiState = _uiState.asStateFlow()

    private val _nombre = MutableStateFlow("")
    val nombre = _nombre.asStateFlow()

    private val _cantidad = MutableStateFlow("")
    val cantidad = _cantidad.asStateFlow()

    private val _fechaVencimiento = MutableStateFlow("")
    val fechaVencimiento = _fechaVencimiento.asStateFlow()

    private val _categoriaId = MutableStateFlow<Int?>(null)
    val categoriaId = _categoriaId.asStateFlow()

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri = _imageUri.asStateFlow()

    fun onNombreChange(value: String) { _nombre.value = value }
    fun onCantidadChange(value: String) { _cantidad.value = value }
    fun onFechaVencimientoChange(value: String) { _fechaVencimiento.value = value }
    fun onCategoriaChange(id: Int) { _categoriaId.value = id }
    fun onImageSelected(uri: Uri?) { _imageUri.value = uri }

    fun getCameraUri(): Uri? = camaraManager.getUri()

    fun createProduct() {
        if (_nombre.value.isBlank()) {
            _uiState.update { it.copy(error = "El nombre es obligatorio") }
            return
        }

        val cantidadInt = _cantidad.value.toIntOrNull() ?: 0
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            // PROCESO DE IMAGEN: Copiamos la imagen temporal a una ruta permanente
            val permanentImagePath = _imageUri.value?.let { 
                imageStorageManager.saveImageLocally(it) 
            }

            val productRequest = ProductActionRequest(
                nombre = _nombre.value,
                cantidad = cantidadInt,
                fechaVencimiento = _fechaVencimiento.value,
                categoriaId = _categoriaId.value,
                imagenUri = permanentImagePath // Guardamos la ruta real, no la temporal
            )

            val result = createProductUseCase(productRequest)
            result.fold(
                onSuccess = { 
                    checkExpiryAndNotify(_nombre.value, _fechaVencimiento.value)
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) } 
                },
                onFailure = { error -> 
                    _uiState.update { it.copy(isLoading = false, error = error.message) } 
                }
            )
        }
    }

    private fun checkExpiryAndNotify(productName: String, expiryDate: String) {
        viewModelScope.launch {
            val user = tokenDataStore.getUser().firstOrNull()
            val userId = user?.id ?: 0
            val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            
            if (expiryDate == todayStr) {
                val title = "¡Alerta de Caducidad!"
                val message = "El producto '$productName' caduca hoy mismo."
                notificationHelper.show(title = title, message = message, type = "inventory_alert")
                notificationDao.insertNotification(
                    NotificationEntity(title = title, message = message, timestamp = System.currentTimeMillis(), usuarioId = userId)
                )
            }
        }
    }

    fun clearError() { _uiState.update { it.copy(error = null) } }
}
