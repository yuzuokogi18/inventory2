package com.example.inventori2.core.di

import com.example.inventori2.core.hardware.data.AndroidBiometriaManager
import com.example.inventori2.core.hardware.data.AndroidCamaraManager
import com.example.inventori2.core.hardware.data.AndroidGaleriaManager
import com.example.inventori2.core.hardware.data.AndroidMovimientoManager
import com.example.inventori2.core.hardware.data.AndroidNotificacionManager
import com.example.inventori2.core.hardware.domain.BiometriaManager
import com.example.inventori2.core.hardware.domain.CamaraManager
import com.example.inventori2.core.hardware.domain.GaleriaManager
import com.example.inventori2.core.hardware.domain.MovimientoManager
import com.example.inventori2.core.hardware.domain.NotificacionManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HardwareModule {

    @Binds
    @Singleton
    abstract fun bindCamaraManager(
        androidCamaraManager: AndroidCamaraManager
    ): CamaraManager

    @Binds
    @Singleton
    abstract fun bindGaleriaManager(
        androidGaleriaManager: AndroidGaleriaManager
    ): GaleriaManager

    @Binds
    @Singleton
    abstract fun bindNotificacionManager(
        androidNotificacionManager: AndroidNotificacionManager
    ): NotificacionManager

    @Binds
    @Singleton
    abstract fun bindBiometriaManager(
        androidBiometriaManager: AndroidBiometriaManager
    ): BiometriaManager

    @Binds
    @Singleton
    abstract fun bindMovimientoManager(
        androidMovimientoManager: AndroidMovimientoManager
    ): MovimientoManager
}
