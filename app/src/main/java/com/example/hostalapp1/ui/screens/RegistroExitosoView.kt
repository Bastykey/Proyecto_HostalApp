package com.example.hostalapp1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostalapp1.PurpleSoft

@Composable
fun RegistroExitosoView(
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Mensaje de confirmación
        Text(
            text = "Registro exitoso 🎉\n\n" +
                    "Te hemos enviado un correo para confirmar tu cuenta.",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón para volver (AppRoot decide a dónde ir)
        Button(
            onClick = onVolver,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Volver al inicio")
        }
    }
}
