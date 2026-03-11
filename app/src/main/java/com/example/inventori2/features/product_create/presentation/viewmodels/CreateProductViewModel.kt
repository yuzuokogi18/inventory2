package com.example.inventori2.features.product_create.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val createProductUseCase: CreateProductUseCase
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

    fun onNombreChange(value: String) { _nombre.value = value }
    fun onCantidadChange(value: String) { _cantidad.value = value }
    fun onFechaVencimientoChange(value: String) { _fechaVencimiento.value = value }
    fun onCategoriaChange(id: Int) { _categoriaId.value = id }

    fun createProduct() {
        if (_nombre.value.isBlank()) {
            _uiState.update { it.copy(error = "El nombre es obligatorio") }
            return
        }

        val cantidadInt = _cantidad.value.toIntOrNull()
        if (cantidadInt == null || cantidadInt <= 0) {
            _uiState.update { it.copy(error = "La cantidad debe ser mayor a 0") }
            return
        }

        if (_fechaVencimiento.value.isBlank()) {
            _uiState.update { it.copy(error = "La fecha es obligatoria") }
            return
        }

        val productRequest = ProductActionRequest(
            nombre = _nombre.value,
            cantidad = cantidadInt,
            fechaVencimiento = _fechaVencimiento.value,
            categoriaId = _categoriaId.value
        )

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = createProductUseCase(productRequest)
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { response ->
                        currentState.copy(
                            isLoading = false,
                            productRequest = productRequest,
                            productResponse = response,
                            isSuccess = true
                        )
                    },
                    onFailure = { error ->
                        currentState.copy(
                            isLoading = false,
                            error = error.message ?: "Error del servidor",
                            isSuccess = false
                        )
                    }
                )
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
