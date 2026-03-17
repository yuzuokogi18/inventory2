package com.example.inventori2.features.product_create.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.core.hardware.domain.CamaraManager
import com.example.inventori2.core.hardware.domain.GaleriaManager
import com.example.inventori2.core.hardware.domain.NotificacionManager
import com.example.inventori2.features.product_create.data.datasources.models.ProductActionRequest
import com.example.inventori2.features.product_create.domain.usecases.CreateProductUseCase
import com.example.inventori2.features.product_create.presentation.screens.CreateProductUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase,
    private val camaraManager: CamaraManager,
    private val galeriaManager: GaleriaManager,
    private val notificacionManager: NotificacionManager
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
            val productRequest = ProductActionRequest(
                nombre = _nombre.value,
                cantidad = cantidadInt,
                fechaVencimiento = _fechaVencimiento.value,
                categoriaId = _categoriaId.value

            )

            val result = createProductUseCase(productRequest)
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { 
                        notificacionManager.mostrarNotificacion("Inventario", "Producto ${_nombre.value} guardado")
                        currentState.copy(isLoading = false, isSuccess = true) 
                    },
                    onFailure = { error -> currentState.copy(isLoading = false, error = error.message) }
                )
            }
        }
    }

    fun clearError() { _uiState.update { it.copy(error = null) } }
}
