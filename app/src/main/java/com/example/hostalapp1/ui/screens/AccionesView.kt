package com.example.hostalapp1.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostalapp1.model.Hostal
import com.example.hostalapp1.model.Rol
import com.example.hostalapp1.viewmodel.HostalViewModel
import com.example.hostalapp1.PurpleSoft

@Composable
fun AccionesView(
    hostales: List<Hostal>,
    rol: Rol,
    viewModel: HostalViewModel,   // 🔹 ViewModel necesario (GPS / SQLite)
    onCrear: () -> Unit,
    onEditar: (Hostal) -> Unit,
    onEliminar: (Hostal) -> Unit,
    onVolver: () -> Unit
) {
    val context = LocalContext.current
    var showHostales by remember { mutableStateOf(false) }

    // 🔹 Copia segura para evitar crash por recomposición
    val hostalesSeguro = remember(hostales) { hostales.toList() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // =====================
        // TÍTULO
        // =====================
        Spacer(modifier = Modifier.height(50.dp))
        Text("¿Qué vamos a hacer?", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(24.dp))

        // =====================
        // BOTÓN CREAR (ADMIN)
        // =====================
        if (rol == Rol.Admin) {
            Button(
                onClick = onCrear,
                colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Crear Hostal")
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // =====================
        // MOSTRAR HOSTALES
        // =====================
        Button(
            onClick = { showHostales = !showHostales },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Ver Hostales")
        }

        // =====================
        // LISTA DE HOSTALES
        // =====================
        AnimatedVisibility(
            visible = showHostales,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Hostales Disponibles", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))

                if (hostalesSeguro.isEmpty()) {
                    Text("No hay hostales registrados...")
                } else {
                    hostalesSeguro.forEach { hostal ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // 🔹 Datos del hostal
                            Text("${hostal.nombre}   ${hostal.precio}")

                            // 🔹 Acciones solo para Admin
                            if (rol == Rol.Admin) {
                                Row {
                                    Button(
                                        onClick = { onEditar(hostal) },
                                        colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
                                    ) {
                                        Text("Editar")
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Button(
                                        onClick = {
                                            onEliminar(hostal)
                                            Toast.makeText(
                                                context,
                                                "Hostal eliminado correctamente",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
                                    ) {
                                        Text("Eliminar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // =====================
        // GPS (RECURSO NATIVO)
        // =====================
        Button(
            onClick = {
                viewModel.obtenerUbicacion()
                Toast.makeText(
                    context,
                    "Obteniendo ubicación GPS...",
                    Toast.LENGTH_SHORT
                ).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Obtener ubicación")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // =====================
        // CERRAR SESIÓN
        // =====================
        Button(
            onClick = onVolver,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Cerrar Sesión")
        }
    }
}
