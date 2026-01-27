package com.example.hostalapp1.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostalapp1.PurpleSoft
import com.example.hostalapp1.model.Rol
import com.example.hostalapp1.auth.validarCredenciales


@Composable
fun LoginView(
    onLoginExitoso: (Rol) -> Unit,
    onRegistrarse: () -> Unit
) {
    val context = LocalContext.current

    // Estados locales del formulario
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    // Estados de error
    var errorUsuario by remember { mutableStateOf(false) }
    var errorContrasena by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "¡Bienvenido a HostalApp!",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Inicia sesión para continuar",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo usuario
        TextField(
            value = usuario,
            onValueChange = {
                usuario = it
                errorUsuario = false
            },
            label = { Text("Usuario") },
            isError = errorUsuario,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        if (errorUsuario) {
            Text(
                text = "El usuario es obligatorio",
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo contraseña
        TextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                errorContrasena = false
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = errorContrasena,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        if (errorContrasena) {
            Text(
                text = "La contraseña es obligatoria",
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón iniciar sesión
        Button(
            onClick = {
                val rol = validarCredenciales(
                    usuario.trim(),
                    contrasena.trim()
                )

                when {
                    rol == null -> {
                        errorUsuario = usuario.isBlank()
                        errorContrasena = contrasena.isBlank()

                        Toast.makeText(
                            context,
                            "Completa todos los campos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        // Se informa el rol al AppRoot
                        onLoginExitoso(rol)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ir a registro
        TextButton(onClick = onRegistrarse) {
            Text("¿No tienes cuenta? Regístrate aquí")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    LoginView(
        onLoginExitoso = {},
        onRegistrarse = {}
    )
}
