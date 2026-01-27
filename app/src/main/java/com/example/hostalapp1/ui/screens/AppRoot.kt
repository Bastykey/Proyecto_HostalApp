package com.example.hostalapp1.ui.screens

import android.app.Application
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hostalapp1.model.Hostal
import com.example.hostalapp1.model.Rol
import com.example.hostalapp1.viewmodel.HostalViewModel
import com.example.hostalapp1.viewmodel.HostalViewModelFactory

@Composable
fun AppRoot() {

    // =====================
    // ESTADOS GENERALES
    // =====================
    var rol by rememberSaveable { mutableStateOf<Rol?>(null) }
    var showRegistro by rememberSaveable { mutableStateOf(false) }
    var showRegistroExitoso by rememberSaveable { mutableStateOf(false) }
    var showCrearHostal by rememberSaveable { mutableStateOf(false) }

    // 🔹 Estados para EDITAR hostal
    var showEditarHostal by rememberSaveable { mutableStateOf(false) }
    var hostalAEditar by remember { mutableStateOf<Hostal?>(null) }

    // 🔹 Application obtenido correctamente
    val application = LocalContext.current.applicationContext as Application

    // 🔹 ViewModel con Factory (FORMA CORRECTA)
    val hostalViewModel: HostalViewModel = viewModel(
        factory = HostalViewModelFactory(application)
    )

    when {

        // =====================
        // REGISTRO EXITOSO
        // =====================
        showRegistroExitoso -> {
            RegistroExitosoView(
                onVolver = {
                    showRegistroExitoso = false
                    rol = null
                }
            )
        }

        // =====================
        // REGISTRO
        // =====================
        showRegistro -> {
            RegistroView(
                onRegistrado = {
                    showRegistro = false
                    showRegistroExitoso = true
                },
                onVolver = { showRegistro = false }
            )
        }

        // =====================
        // EDITAR HOSTAL (ADMIN)
        // =====================
        showEditarHostal && hostalAEditar != null -> {
            EditarHostalView(
                hostal = hostalAEditar!!,
                onGuardar = { hostalEditado ->
                    hostalViewModel.editarHostal(
                        original = hostalAEditar!!,
                        editado = hostalEditado
                    )
                    hostalAEditar = null
                    showEditarHostal = false
                },
                onVolver = {
                    hostalAEditar = null
                    showEditarHostal = false
                }
            )
        }

        // =====================
        // CREAR HOSTAL (ADMIN)
        // =====================
        showCrearHostal -> {
            CrearHostalView(
                onGuardar = { hostal ->
                    hostalViewModel.agregarHostal(hostal)
                    showCrearHostal = false
                },
                onVolver = { showCrearHostal = false }
            )
        }

        // =====================
        // LOGIN
        // =====================
        rol == null -> {
            LoginView(
                onLoginExitoso = { rolLogueado ->
                    rol = rolLogueado
                },
                onRegistrarse = { showRegistro = true }
            )
        }

        // =====================
        // CLIENTE
        // =====================
        rol == Rol.Cliente -> {
            ClienteView(
                viewModel = hostalViewModel,
                onVolver = { rol = null }
            )
        }

        // =====================
        // ADMIN
        // =====================
        else -> {
            AccionesView(
                hostales = hostalViewModel.hostales,
                rol = rol!!,
                viewModel = hostalViewModel,

                onCrear = {
                    showCrearHostal = true
                },

                onEditar = { hostal ->
                    hostalAEditar = hostal
                    showEditarHostal = true
                },

                onEliminar = { hostal ->
                    hostalViewModel.eliminarHostal(hostal)
                },

                onVolver = {
                    rol = null
                }
            )
        }
    }
}
