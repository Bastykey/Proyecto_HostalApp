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
import androidx.compose.ui.graphics.Color
import com.example.hostalapp1.model.Hostal

@Composable
fun ClienteView(
    viewModel: HostalViewModel,
    onVolver: () -> Unit
) {
    val context = LocalContext.current

    //  Estados para reserva
    var showReserva by remember { mutableStateOf(false) }
    var hostalSeleccionado by remember { mutableStateOf<Hostal?>(null) }

    //  Si está reservando, se muestra la vista de reserva
    if (showReserva && hostalSeleccionado != null) {
        ReservarHostalView(
            hostal = hostalSeleccionado!!,
            onReservaExitosa = {
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

    //  Cargar hostales externos al entrar a la vista
    LaunchedEffect(Unit) {
        viewModel.cargarHostalesExternos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hostales Disponibles", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(16.dp))

        //  loader
        if (viewModel.cargando.value) {
            CircularProgressIndicator(color = PurpleSoft)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Cargando hostales...")
        }

        //  error
        viewModel.error.value?.let {
            Text(it, color = Color.Red)
        }

        //  es la lista de hostales
        if (!viewModel.cargando.value && viewModel.hostales.isNotEmpty()) {
            viewModel.hostales.forEach { hostal ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text("${hostal.nombre}   ${hostal.precio}")

                    // 🔹 Botón reservar
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

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onVolver,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Cerrar Sesión")
        }
    }
}
