package com.example.hostalapp1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostalapp1.ui.theme.HostalApp1Theme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


private val PurpleSoft = Color(0xFF7E57C2)

data class Hostal(
    val nombre: String,
    val precio: String
)

enum class Rol {
    Admin,
    Cliente
}

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

    val navController = rememberNavController()
    val hostales = remember { mutableStateListOf<Hostal>() }
    var hostalSeleccionado by remember { mutableStateOf<Hostal?>(null) }

    NavHost(
        navController = navController,
        startDestination = "rol",
        modifier = modifier
    ) {

        composable("rol") {
            SeleccionarRolView(
                onSeleccionarAdmin = {
                    navController.navigate("admin")
                },
                onSeleccionarCliente = {
                    navController.navigate("cliente")
                }
            )
        }

        composable("cliente") {
            ClienteView(
                hostales = hostales,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("admin") {
            AccionesView(
                hostales = hostales,
                rol = Rol.Admin,
                onCrear = {
                    navController.navigate("crear")
                },
                onEditar = {
                    hostalSeleccionado = it
                    navController.navigate("editar")
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("crear") {
            CrearHostalView(
                onGuardar = {
                    hostales.add(it)
                    navController.popBackStack()
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("editar") {
            hostalSeleccionado?.let { hostal ->
                EditarHostalView(
                    hostal = hostal,
                    onGuardar = { editado ->
                        val index = hostales.indexOf(hostal)
                        if (index != -1) {
                            hostales[index] = editado
                        }
                        navController.popBackStack()
                    },
                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
fun SeleccionarRolView(
    onSeleccionarAdmin: () -> Unit,
    onSeleccionarCliente: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¡Bienvenido a HostalApp!", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onSeleccionarAdmin,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Admin")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSeleccionarCliente,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Cliente")
        }
    }
}

@Composable
fun ClienteView(
    hostales: SnapshotStateList<Hostal>,
    onVolver: () -> Unit
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Hostales Disponibles", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(16.dp))

        if (hostales.isEmpty()) {
            Text("No hay hostales registrados...")
        } else {
            hostales.forEach { hostal ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text("${hostal.nombre}   ${hostal.precio}")

                    Button(
                        onClick = {
                            Toast.makeText(
                                context,
                                "Hostal seleccionado: ${hostal.nombre}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
                    ) {
                        Text("Seleccionar")
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
            Text("Volver a selección")
        }
    }
}

@Composable
fun AccionesView(
    hostales: SnapshotStateList<Hostal>,
    rol: Rol,
    onCrear: () -> Unit,
    onEditar: (Hostal) -> Unit,
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

        Text("¿Qué vamos a hacer?", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(24.dp))

        if (rol == Rol.Admin) {
            Button(
                onClick = onCrear,
                colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Crear Hostal")
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
            onClick = { showHostales = !showHostales },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Ver Hostales")
        }

        AnimatedVisibility(
            visible = showHostales,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column {

                Spacer(modifier = Modifier.height(24.dp))
                Text("Hostales Disponibles", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))

                if (hostales.isEmpty()) {
                    Text("No hay hostales registrados...")
                } else {
                    hostales.forEach { hostal ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text("${hostal.nombre}   ${hostal.precio}")

                            if (rol == Rol.Admin) {
                                Row {
                                    Button(
                                        onClick = { onEditar(hostal) },
                                        colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
                                    ) {
                                        Text("Editar")
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Button(
                                        onClick = { hostalAEliminar = hostal },
                                        colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
                                    ) {
                                        Text("Eliminar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        hostalAEliminar?.let {
            hostales.remove(it)
            hostalAEliminar = null
            showHostales = false
            Toast.makeText(context, "Hostal eliminado correctamente", Toast.LENGTH_SHORT).show()
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onVolver,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
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

        Text("Crear Hostal", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(24.dp))

        TextField(nombre, { nombre = it }, label = { Text("Nombre del Hostal") })

        Spacer(modifier = Modifier.height(12.dp))

        TextField(precio, { precio = it }, label = { Text("Precio por noche") })

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && precio.isNotBlank()) {
                    onGuardar(Hostal(nombre, precio))
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onVolver,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
        ) {
            Text("Volver")
        }
    }
}

@Composable
fun EditarHostalView(
    hostal: Hostal,
    onGuardar: (Hostal) -> Unit,
    onVolver: () -> Unit
) {

    var nombre by remember { mutableStateOf(hostal.nombre) }
    var precio by remember { mutableStateOf(hostal.precio) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Actualizar Hostal", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(24.dp))

        TextField(nombre, { nombre = it }, label = { Text("Nombre del Hostal") })

        Spacer(modifier = Modifier.height(12.dp))

        TextField(precio, { precio = it }, label = { Text("Precio por noche") })

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && precio.isNotBlank()) {
                    onGuardar(Hostal(nombre, precio))
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
        ) {
            Text("Guardar cambios")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onVolver,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
        ) {
            Text("Volver")
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

