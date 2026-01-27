package com.example.hostalapp1.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostalapp1.viewmodel.HostalViewModel
import com.example.hostalapp1.PurpleSoft
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import com.example.hostalapp1.model.Hostal

@Composable
fun ClienteView(
    viewModel: HostalViewModel,
    onVolver: () -> Unit
) {
    val context = LocalContext.current

    // 🔹 Estados para reserva
    var showReserva by remember { mutableStateOf(false) }
    var hostalSeleccionado by remember { mutableStateOf<Hostal?>(null) }

    // 🔹 Vista de reserva
    if (showReserva && hostalSeleccionado != null) {
        ReservarHostalView(
            hostal = hostalSeleccionado!!,
            onReservaExitosa = {
                Toast.makeText(
                    context,
                    "Has reservado con éxito ✅",
                    Toast.LENGTH_SHORT
                ).show()
                showReserva = false
                hostalSeleccionado = null
            },
            onVolver = {
                showReserva = false
                hostalSeleccionado = null
            }
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔹 Botón volver (arriba, claro)
        Button(
            onClick = onVolver,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("⬅ Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Hostales Disponibles",
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Loader
        if (viewModel.cargando.value) {
            CircularProgressIndicator(
                color = PurpleSoft,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Cargando hostales...")
        }

        // 🔹 Error (si existe)
        viewModel.error.value?.let {
            Text(it, color = Color.Red)
        }

        // 🔹 Lista ordenada
        LazyColumn {
            items(viewModel.hostales) { hostal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {
                            Text(hostal.nombre, fontSize = 16.sp)
                            Text(hostal.precio, color = Color.Gray)
                        }

                        Button(
                            onClick = {
                                hostalSeleccionado = hostal
                                showReserva = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
                        ) {
                            Text("Reservar")
                        }
                    }
                }
            }
        }
    }
}

