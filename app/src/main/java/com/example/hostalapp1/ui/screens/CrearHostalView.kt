package com.example.hostalapp1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostalapp1.model.Hostal
import com.example.hostalapp1.PurpleSoft

@Composable
fun CrearHostalView(
    onGuardar: (Hostal) -> Unit,
    onVolver: () -> Unit
) {
    // Estados locales de los campos
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear Hostal",
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo nombre del hostal
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Hostal") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo precio
        TextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio por noche") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón guardar
        Button(
            onClick = {
                // Validación simple
                if (nombre.isNotBlank() && precio.isNotBlank()) {
                    // Se envía el hostal al AppRoot
                    onGuardar(
                        Hostal(
                            nombre = nombre,
                            precio = precio
                        )
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón volver (AppRoot decide a dónde volver)
        Button(
            onClick = onVolver,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
        ) {
            Text("Volver")
        }
    }
}
