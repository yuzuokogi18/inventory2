package com.example.inventori2.features.product_delete.presentation.viewmodels

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventori2.features.product_delete.domain.usecases.DeleteProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDeleteViewModel @Inject constructor(
    private val deleteProductUseCase: DeleteProductUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _isDeleted = MutableStateFlow(false)
    val isDeleted = _isDeleted.asStateFlow()

    fun deleteProduct(productId: Int) {
        _isLoading.value = true
        _error.value = null
        
        viewModelScope.launch {
            val result = deleteProductUseCase(productId)
            result.fold(
                onSuccess = {
                    _isLoading.value = false
                    _isDeleted.value = true
                    vibratePhone() // Llamada al hardware
                },
                onFailure = { error ->
                    _isLoading.value = false
                    _error.value = error.message ?: "Error al eliminar el producto"
                }
            )
        }
    }

    private fun vibratePhone() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(200)
        }
    }

    fun clearError() { _error.value = null }
    fun resetDeleteStatus() { _isDeleted.value = false }
}
