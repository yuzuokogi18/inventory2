package com.example.inventori2.core.hardware.domain

interface ExportadorManager {
    fun exportarListaAArchivo(titulo: String, items: List<String>)
}
