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
import androidx.compose.ui.text.input.PasswordVisualTransformation

private val PurpleSoft = Color(0xFF7E57C2)

data class Hostal(
    val nombre: String,
    val precio: String
)

enum class Rol {
    Admin,
    Cliente
}

data class Usuario(
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val rut: String,
    val correo: String,
    val telefono: String
)

private const val ADMIN_USUARIO = "Admin"
private const val ADMIN_CONTRASEÑA = "Admin 1"

fun validarCredenciales(usuario: String, contraseña: String): Rol? {
    return when {
        usuario.isBlank() || contraseña.isBlank() -> null
        usuario == ADMIN_USUARIO && contraseña == ADMIN_CONTRASEÑA -> Rol.Admin
        else -> Rol.Cliente
    }
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
    var showCrearHostal by remember { mutableStateOf(false) }
    var showEditarHostal by remember { mutableStateOf(false) }
    var hostalSeleccionado by remember { mutableStateOf<Hostal?>(null) }
    var rolSeleccionado by remember { mutableStateOf<Rol?>(null) }
    val hostales = remember { mutableStateListOf<Hostal>() }
    var showRegistro by remember { mutableStateOf(false) }
    var showRegistroExitoso by remember { mutableStateOf(false) }

    when {
        // Vista de Registro Exitoso
        showRegistroExitoso -> {
            RegistroExitosoView(
                onVolver = {
                    showRegistroExitoso = false
                    rolSeleccionado = null
                }
            )
        }

        // Vista de Registro
        showRegistro -> {
            RegistroView(
                onRegistrado = {
                    showRegistro = false
                    showRegistroExitoso = true
                },
                onVolver = {
                    showRegistro = false
                }
            )
        }

        //  Login
        rolSeleccionado == null -> {
            LoginView(
                onLoginExitoso = { rol ->
                    rolSeleccionado = rol
                },
                onRegistrarse = {
                    showRegistro = true
                }
            )
        }

        //  Cliente
        rolSeleccionado == Rol.Cliente -> {
            ClienteView(
                hostales = hostales,
                onVolver = { rolSeleccionado = null }
            )
        }

        //  Admin - Crear
        rolSeleccionado == Rol.Admin && showCrearHostal -> {
            CrearHostalView(
                onGuardar = {
                    hostales.add(it)
                    showCrearHostal = false
                },
                onVolver = { showCrearHostal = false }
            )
        }

        //  Admin - Editar
        rolSeleccionado == Rol.Admin && showEditarHostal && hostalSeleccionado != null -> {
            EditarHostalView(
                hostal = hostalSeleccionado!!,
                onGuardar = { editado ->
                    val index = hostales.indexOf(hostalSeleccionado)
                    if (index != -1) {
                        hostales[index] = editado
                    }
                    hostalSeleccionado = null
                    showEditarHostal = false
                },
                onVolver = {
                    hostalSeleccionado = null
                    showEditarHostal = false
                }
            )
        }

        // Menú principal
        else -> {
            AccionesView(
                hostales = hostales,
                rol = rolSeleccionado!!,
                onCrear = { showCrearHostal = true },
                onEditar = {
                    hostalSeleccionado = it
                    showEditarHostal = true
                },
                onVolver = { rolSeleccionado = null }
            )
        }
    }
}

@Composable
fun LoginView(
    onLoginExitoso: (Rol) -> Unit,
    onRegistrarse: () -> Unit
) {
    val context = LocalContext.current
    var usuario by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var errorUsuario by remember { mutableStateOf(false) }
    var errorContraseña by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¡Bienvenido a HostalApp!", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Inicia sesión para continuar", fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))

        //
        TextField(
            value = usuario,
            onValueChange = {
                usuario = it
                errorUsuario = false
            },
            label = { Text("Usuario") },
            singleLine = true,
            isError = errorUsuario,
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        if (errorUsuario) {
            Text(
                text = "El usuario es requerido",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = contraseña,
            onValueChange = {
                contraseña = it
                errorContraseña = false
            },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = errorContraseña,
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        if (errorContraseña) {
            Text(
                text = "La contraseña es requerida",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val rol = validarCredenciales(usuario.trim(), contraseña.trim())
                when {
                    rol == null -> {
                        errorUsuario = usuario.isBlank()
                        errorContraseña = contraseña.isBlank()
                        Toast.makeText(
                            context,
                            "Por favor, completa todos los campos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    rol == Rol.Admin -> {
                        onLoginExitoso(rol)
                    }
                    else -> {
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

        // Boton para registrarse
        TextButton(onClick = onRegistrarse) {
            Text("¿No tienes cuenta? Registrate aqui")
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
            Text("Cerrar Sesión")
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
            Text("Cerrar Sesión")
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
                    onGuardar(Hostal(nombre = nombre, precio = precio))
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
                    onGuardar(Hostal(nombre = nombre, precio = precio))
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

@Composable
fun RegistroView(
    onRegistrado: (Usuario) -> Unit,
    onVolver: () -> Unit
) {
    val context = LocalContext.current

    // Variables de los campos del formulario
    var nombre by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    // Variables de control de errores por campo
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

        // Campo Nombres
        TextField(
            value = nombre,
            onValueChange = {
                nombre = it
                errorNombre = false // Se limpia el error al escribir
            },
            label = { Text("Nombres ") },
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

        // Campo RUT
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

        // Botón de registro
        Button(
            onClick = {
                // Validación de campos obligatorios
                errorNombre = nombre.isBlank()
                errorApellidoPaterno = apellidoPaterno.isBlank()
                errorApellidoMaterno = apellidoMaterno.isBlank()
                errorRut = rut.isBlank()
                errorCorreo = correo.isBlank()
                errorTelefono = telefono.isBlank()

                // Si no hay errores, se registra el usuario
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
                            nombre,
                            apellidoPaterno,
                            apellidoMaterno,
                            rut,
                            correo,
                            telefono
                        )
                    )
                    // Mensaje de confirmación
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

        // Botón para volver manualmente al login
        Button(
            onClick = onVolver,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleSoft)
        ) {
            Text("Volver al Login")
        }
    }
}

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
        Text(
            "Se ha registrado correctamente, te hemos enviado un correo para confirmar tu correo.",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HostalApp1Theme {
        Greeting()
    }
}
