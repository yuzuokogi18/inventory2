package com.example.inventori2.features.product_edit.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.core.hardware.domain.CamaraManager
import com.example.inventori2.features.product_detail.domain.usecases.GetProductByIdUseCase
import com.example.inventori2.features.product_edit.domain.entities.ProductEdit
import com.example.inventori2.features.product_edit.domain.usecases.UpdateProductUseCase
import com.example.inventori2.features.product_edit.presentation.screens.ProductEditUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductEditViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val camaraManager: CamaraManager // Hardware añadido
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductEditUIState())
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

    private var currentProductId: Int = -1

    fun onNombreChange(value: String) { _nombre.value = value }
    fun onCantidadChange(value: String) { _cantidad.value = value }
    fun onFechaVencimientoChange(value: String) { _fechaVencimiento.value = value }
    fun onCategoriaChange(id: Int) { _categoriaId.value = id }
    fun onImageSelected(uri: Uri?) { _imageUri.value = uri }
    fun getCameraUri(): Uri? = camaraManager.getUri()

    fun loadProduct(productId: Int) {
        currentProductId = productId
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = getProductByIdUseCase(productId)
            result.fold(
                onSuccess = { product ->
                    _nombre.value = product.nombre
                    _cantidad.value = product.cantidad.toString()
                    _fechaVencimiento.value = product.fechaVencimiento ?: ""
                    _categoriaId.value = product.categoriaId
                    _uiState.update { it.copy(isLoading = false) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }

    fun updateProduct() {
        if (_nombre.value.isBlank() || _cantidad.value.isBlank()) {
            _uiState.update { it.copy(error = "Nombre y cantidad son obligatorios") }
            return
        }
        val cantidadInt = _cantidad.value.toIntOrNull() ?: 0
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val productEdit = ProductEdit(
                id = currentProductId,
                nombre = _nombre.value,
                cantidad = cantidadInt,
                fechaVencimiento = _fechaVencimiento.value,
                categoriaId = _categoriaId.value
            )
            val result = updateProductUseCase(productEdit)
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { currentState.copy(isLoading = false, isSuccess = true) },
                    onFailure = { error -> currentState.copy(isLoading = false, error = error.message) }
                )
            }
        }
    }

    fun clearError() { _uiState.update { it.copy(error = null) } }
}
