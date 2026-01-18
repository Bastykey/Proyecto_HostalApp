package com.example.hostalapp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostalapp1.ui.theme.HostalApp1Theme
import android.widget.Toast
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext


data class Hostal(
    val nombre: String,
    val precio: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HostalApp1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {

    var showActions by remember { mutableStateOf(false) }
    var showCrearHostal by remember { mutableStateOf(false) }

    val hostales = remember { mutableStateListOf<Hostal>() }

    when {
        !showActions -> {
            IngresarView(onIngresar = { showActions = true })
        }

        showCrearHostal -> {
            CrearHostalView(
                onGuardar = { hostal ->
                    hostales.add(hostal)
                    showCrearHostal = false
                },
                onVolver = { showCrearHostal = false }
            )
        }

        else -> {
            AccionesView(
                hostales = hostales,
                onCrear = { showCrearHostal = true },
                onVolver = { showActions = false }
            )
        }
    }
}

@Composable
fun IngresarView(onIngresar: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "¡Bienvenido a HostalApp!", fontSize = 26.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onIngresar,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Ingresar")
        }
    }
}


@Composable
fun AccionesView(
    hostales: SnapshotStateList<Hostal>,
    onCrear: () -> Unit,
    onVolver: () -> Unit
) {

    val context = LocalContext.current
    var showHostales by remember { mutableStateOf(false) }
    var hostalAEliminar by remember { mutableStateOf<Hostal?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "¿Qué vamos a hacer?", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onCrear,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Crear Hostal")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { showHostales = !showHostales },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Ver Hostales")
        }

        if (showHostales) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Hostales Disponibles", fontSize = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))

            if (hostales.isEmpty()) {
                Text(text = "No hay hostales registrados. . .")
            } else {
                hostales.forEach { hostal ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(text = "${hostal.nombre}   ${hostal.precio}")

                        Button(
                            onClick = {
                                hostalAEliminar = hostal
                            }
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }

        hostalAEliminar?.let { hostal ->
            hostales.remove(hostal)
            hostalAEliminar = null
            showHostales = false

            Toast.makeText(
                context,
                "Hostal eliminado correctamente",
                Toast.LENGTH_SHORT
            ).show()
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Actualizar Hostal", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Actualizar Hostal")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Volver al inicio")
        }
    }
}

@Composable
fun CrearHostalView(
    onGuardar: (Hostal) -> Unit,
    onVolver: () -> Unit
) {

    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Crear Hostal", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Hostal") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio por noche") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && precio.isNotBlank()) {
                    onGuardar(Hostal(nombre, precio))
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Volver  hacia atrás")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HostalApp1Theme {
        Greeting()
    }
}
