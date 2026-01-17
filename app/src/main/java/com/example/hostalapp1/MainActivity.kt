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
import androidx.compose.runtime.Composable
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
            Toast.makeText(context, "Ver hostales", Toast.LENGTH_SHORT).show()
        }) {
            Text("Ingresar ")
        }

        Spacer(modifier = Modifier.height(32.dp))
        // operadores crud
        Text(text = "¿ Que Vamos a Hacer ?")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            Toast.makeText(context, "Crear Hostal", Toast.LENGTH_SHORT).show()
        }) {
            Text("Crear Hostal")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            Toast.makeText(context, "Leer Hostales", Toast.LENGTH_SHORT).show()
        }) {
            Text("Leer Hostales")
        }

        Spacer(modifier = Modifier.height(8.dp))

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
