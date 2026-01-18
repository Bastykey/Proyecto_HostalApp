package com.example.hostalapp1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostalapp1.ui.theme.HostalApp1Theme

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

    if (!showActions) {
        IngresarView(onIngresar = { showActions = true })
    } else {
        AccionesView(onVolver = { showActions = false })
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
fun AccionesView(onVolver: () -> Unit) {

    val context = LocalContext.current
    var showHostales by remember { mutableStateOf(false) }

    val hostales = listOf(
        "Hostal Plaza Centro   $25.000",
        "Hostal Santiago        $37.000",
        "Hostal Costanera      $43.000",
        "Hostal Vitacura       $55.000",
        "Hostal Las Condes    $79.000"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "¿Qué vamos a hacer?", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Crear Hostal", Toast.LENGTH_SHORT).show()
            },
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

            hostales.forEach { hostal ->
                Text(text = hostal)
            }
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

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Eliminar Hostal", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Eliminar Hostal")
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HostalApp1Theme {
        Greeting()
    }
}
