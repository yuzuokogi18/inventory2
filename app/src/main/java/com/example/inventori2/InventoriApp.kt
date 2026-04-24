package com.example.inventori2

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.example.inventori2.core.notification.NotificationChannels
import com.example.inventori2.features.notification.data.workers.ExpiryWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class InventoriApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        NotificationChannels.createAll(this)

        setupExpiryWorker()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private fun setupExpiryWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val expiryWorkRequest = PeriodicWorkRequestBuilder<ExpiryWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .addTag("expiry_check")
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "expiry_check_work",
            ExistingPeriodicWorkPolicy.KEEP,
            expiryWorkRequest
        )
    }
}
