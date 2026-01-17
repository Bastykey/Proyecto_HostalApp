package com.example.hostalapp1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    var showHostales by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Bienvenido a HostalApp",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            Toast.makeText(context, "Ingresando...", Toast.LENGTH_SHORT).show()
        }) {
            Text("Ingresar")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "¿Qué vamos a hacer?")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            Toast.makeText(context, "Crear Hostal", Toast.LENGTH_SHORT).show()
        }) {
            Text("Crear Hostal")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            showHostales = true
        }) {
            Text("Ver Hostales")
        }

        if (showHostales) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Hostales Disponibles", fontSize = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Hostal Plaza Centro - $25.000")
            Text(text = "Hostal Santiago - $37.000")
            Text(text = "Hostal Costanera - $43.000")
            Text(text = "Hostal Vitacura - $55.000")
            Text(text = "Hostal Las Condes - $79.000")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            Toast.makeText(context, "Actualizar Hostal", Toast.LENGTH_SHORT).show()
        }) {
            Text("Actualizar Hostal")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            Toast.makeText(context, "Eliminar Hostal", Toast.LENGTH_SHORT).show()
        }) {
            Text("Eliminar Hostal")
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
