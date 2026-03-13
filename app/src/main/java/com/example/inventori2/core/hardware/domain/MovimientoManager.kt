package com.example.inventori2.core.hardware.domain

interface MovimientoManager {
    fun iniciarDeteccionDeAgitado(onShake: () -> Unit)
    fun detenerDeteccion()
}