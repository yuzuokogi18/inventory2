package com.example.inventori2.core.hardware.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.inventori2.core.hardware.domain.ExportadorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class AndroidExportadorManager @Inject constructor(
    @ApplicationContext private val context: Context
) : ExportadorManager {

    override fun exportarListaAArchivo(titulo: String, items: List<String>) {
        if (items.isEmpty()) {
            Toast.makeText(context, "No hay artículos para descargar", Toast.LENGTH_SHORT).show()
            return
        }
        
        try {
            val fileName = "Lista_Compras_${System.currentTimeMillis()}.txt"
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val file = File(storageDir, fileName)
            
            FileOutputStream(file).use { output ->
                output.write("$titulo\n".toByteArray())
                output.write("==========================\n".toByteArray())
                items.forEach { item ->
                    output.write("- $item\n".toByteArray())
                }
                output.write("==========================\n".toByteArray())
                output.write("Generado por Inventori App\n".toByteArray())
            }

            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            // Intent para compartir/guardar el archivo
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooserIntent = Intent.createChooser(shareIntent, "Descargar o compartir lista")
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            
            context.startActivity(chooserIntent)
            Toast.makeText(context, "Archivo generado: $fileName", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error al crear el archivo: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
