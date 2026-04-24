package com.example.inventori2.core.hardware.data

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageStorageManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun saveImageLocally(uri: Uri): String? {
        return try {
            val fileName = "prod_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)
            
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
