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
fun EditarHostalView(
    hostal: Hostal,
    onGuardar: (Hostal) -> Unit,
    onVolver: () -> Unit
) {
    // Estados locales inicializados con los datos del hostal seleccionado
    var nombre by remember { mutableStateOf(hostal.nombre) }
    var precio by remember { mutableStateOf(hostal.precio) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Editar Hostal",
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo nombre
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

        // Botón guardar cambios
        Button(
            onClick = {
                // Validación simple
                if (nombre.isNotBlank() && precio.isNotBlank()) {
                    // Se envía el hostal editado al AppRoot
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
            Text("Guardar cambios")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón volver
        Button(
            onClick = onVolver,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
        ) {
            Text("Volver")
        }
    }
}
