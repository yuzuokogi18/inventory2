package com.example.inventori2.features.inventory.product_list.presentation.components.organims

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProductCard(
    title: String,
    cantidad: Int,
    fechaVencimiento: String,
    categoria: String = "General",
    imagenUri: String? = null, // NUEVO
    onEditClick: () -> Unit,
    onViewClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isUrgent = cantidad <= 3 
    
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .border(1.dp, Color(0xFFF3F4F6), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        onClick = onViewClick
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // IMAGEN REAL DEL PRODUCTO
            Box(modifier = Modifier.size(80.dp)) {
                if (imagenUri != null) {
                    AsyncImage(
                        model = Uri.parse(imagenUri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF9FAFB)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.Image, contentDescription = null, tint = Color.LightGray)
                    }
                }
                
                if (isUrgent) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 4.dp, y = (-4).dp)
                            .background(Color(0xFFEF4444), CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 1
                    )
                    Surface(
                        color = Color(0xFFDCFCE7),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = categoria,
                            color = Color(0xFF22C55E),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Outlined.Inventory2, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                    Text(
                        text = "Stock: $cantidad unidades",
                        fontSize = 13.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Icon(imageVector = Icons.Outlined.History, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                    Text(
                        text = "Vence: $fechaVencimiento",
                        fontSize = 13.sp,
                        color = Color(0xFFEF4444),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = if (isUrgent) Color(0xFFFEE2E2) else Color(0xFFF3F4F6),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (isUrgent) "Urgente" else "Fresco",
                            color = if (isUrgent) Color(0xFFEF4444) else Color.Gray,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Row {
                        IconButton(onClick = onEditClick, modifier = Modifier.size(32.dp)) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        }
                        IconButton(onClick = onDeleteClick, modifier = Modifier.size(32.dp)) {
                            Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Icon(imageVector: androidx.compose.ui.graphics.vector.ImageVector, contentDescription: String?, modifier: Modifier = Modifier, tint: Color = Color.Unspecified) {
    androidx.compose.material3.Icon(imageVector = imageVector, contentDescription = contentDescription, modifier = modifier, tint = tint)
}
