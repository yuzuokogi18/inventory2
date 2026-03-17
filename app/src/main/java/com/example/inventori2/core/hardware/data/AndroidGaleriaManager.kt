package com.example.inventori2.core.hardware.data

import com.example.inventori2.core.hardware.domain.GaleriaManager
import javax.inject.Inject

class AndroidGaleriaManager @Inject constructor() : GaleriaManager {
    
    override fun getMimeType(): String {
        return "image/*"
    }
}
