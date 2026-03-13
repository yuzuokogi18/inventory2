package com.example.inventori2.core.hardware.data

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.example.inventori2.core.hardware.domain.CamaraManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class AndroidCamaraManager @Inject constructor(
    @ApplicationContext private val context: Context
) : CamaraManager {

    override fun getUri(): Uri? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }
}
