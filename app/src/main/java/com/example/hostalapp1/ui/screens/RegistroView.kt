package com.example.hostalapp1.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostalapp1.PurpleSoft
import com.example.hostalapp1.model.Usuario

@Composable
fun RegistroView(
    onRegistrado: (Usuario) -> Unit,
    onVolver: () -> Unit
) {
    val context = LocalContext.current
    var nombre by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    // Estados de error por campo
    var errorNombre by remember { mutableStateOf(false) }
    var errorApellidoPaterno by remember { mutableStateOf(false) }
    var errorApellidoMaterno by remember { mutableStateOf(false) }
    var errorRut by remember { mutableStateOf(false) }
    var errorCorreo by remember { mutableStateOf(false) }
    var errorTelefono by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Registro de Usuario", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(24.dp))

        // Campo Nombre
        TextField(
            value = nombre,
            onValueChange = {
                nombre = it
                errorNombre = false
            },
            label = { Text("Nombres") },
            isError = errorNombre
        )

        if (errorNombre) {
            Text("El nombre es obligatorio", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Apellido Paterno
        TextField(
            value = apellidoPaterno,
            onValueChange = {
                apellidoPaterno = it
                errorApellidoPaterno = false
            },
            label = { Text("Apellido Paterno") },
            isError = errorApellidoPaterno
        )

        if (errorApellidoPaterno) {
            Text("El apellido paterno es obligatorio", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Apellido Materno
        TextField(
            value = apellidoMaterno,
            onValueChange = {
                apellidoMaterno = it
                errorApellidoMaterno = false
            },
            label = { Text("Apellido Materno") },
            isError = errorApellidoMaterno
        )

        if (errorApellidoMaterno) {
            Text("El apellido materno es obligatorio", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo rut
        TextField(
            value = rut,
            onValueChange = {
                rut = it
                errorRut = false
            },
            label = { Text("RUT") },
            isError = errorRut
        )

        if (errorRut) {
            Text("El RUT es obligatorio", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Correo
        TextField(
            value = correo,
            onValueChange = {
                correo = it
                errorCorreo = false
            },
            label = { Text("Correo Electrónico") },
            isError = errorCorreo
        )

        if (errorCorreo) {
            Text("El correo es obligatorio", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Teléfono
        TextField(
            value = telefono,
            onValueChange = {
                telefono = it
                errorTelefono = false
            },
            label = { Text("Teléfono") },
            isError = errorTelefono
        )

        if (errorTelefono) {
            Text("El teléfono es obligatorio", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Registrar
        Button(
            onClick = {
                // Validación simple
                errorNombre = nombre.isBlank()
                errorApellidoPaterno = apellidoPaterno.isBlank()
                errorApellidoMaterno = apellidoMaterno.isBlank()
                errorRut = rut.isBlank()
                errorCorreo = correo.isBlank()
                errorTelefono = telefono.isBlank()

                // Si todo está correcto
                if (
                    !errorNombre &&
                    !errorApellidoPaterno &&
                    !errorApellidoMaterno &&
                    !errorRut &&
                    !errorCorreo &&
                    !errorTelefono
                ) {
                    onRegistrado(
                        Usuario(
                            nombre = nombre,
                            apellidoPaterno = apellidoPaterno,
                            apellidoMaterno = apellidoMaterno,
                            rut = rut,
                            correo = correo,
                            telefono = telefono
                        )
                    )

                    Toast.makeText(
                        context,
                        "Registro exitoso",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Registrarse ahora")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Volver al login
        Button(
            onClick = onVolver,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
        ) {
            Text("Volver al Login")
        }
    }
}
