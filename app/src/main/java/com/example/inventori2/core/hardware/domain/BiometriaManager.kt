package com.example.inventori2.core.hardware.domain

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity

interface BiometriaManager {
    fun estaDisponible(): Boolean
    fun autenticar(
        activity: FragmentActivity,
        titulo: String,
        subtitulo: String,
        onSuccess: (BiometricPrompt.AuthenticationResult) -> Unit,
        onError: (Int, CharSequence) -> Unit
    )
}