package com.example.hostalapp1.ui.screens



import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostalapp1.model.Hostal
import com.example.hostalapp1.PurpleSoft

@Composable
fun ReservarHostalView(
    hostal: Hostal,
    onReservaExitosa: () -> Unit,
    onVolver: () -> Unit
) {
    val context = LocalContext.current

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Reservar Hostal", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = hostal.nombre,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        //  Nombre del cliente
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Correo
        TextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // boton para Confirmar reserva
        Button(
            onClick = {
                if (nombre.isNotBlank() && correo.isNotBlank()) {
                    Toast.makeText(
                        context,
                        "Has reservado con éxito",
                        Toast.LENGTH_LONG
                    ).show()

                    onReservaExitosa()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Confirmar Reserva")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // boton para Volver
        Button(
            onClick = onVolver,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
        ) {
            Text("Volver")
        }
    }
}
