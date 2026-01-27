package com.example.hostalapp1.ui.screens

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hostalapp1.model.Rol
import com.example.hostalapp1.viewmodel.HostalViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
@Composable
fun AppRoot() {

    var rol by rememberSaveable { mutableStateOf<Rol?>(null) }
    var showRegistro by rememberSaveable { mutableStateOf(false) }
    var showRegistroExitoso by rememberSaveable { mutableStateOf(false) }
    val hostalViewModel: HostalViewModel = viewModel()

    when {

        // Registro exitoso
        showRegistroExitoso -> {
            RegistroExitosoView(
                onVolver = {    
                    showRegistroExitoso = false
                    rol = null
                }
            )
        }

        // Registro
        showRegistro -> {
            RegistroView(
                onRegistrado = {
                    showRegistro = false
                    showRegistroExitoso = true
                },
                onVolver = { showRegistro = false }
            )
        }

        // Login
        rol == null -> {
            LoginView(
                onLoginExitoso = { rolLogueado ->
                    rol = rolLogueado
                },
                onRegistrarse = { showRegistro = true }
            )
        }

        // Cliente
        rol == Rol.Cliente -> {
            ClienteView(
                viewModel = hostalViewModel,
                onVolver = { rol = null }
            )
        }

        // Admin
        else -> {
            AccionesView(
                hostales = hostalViewModel.hostales,
                rol = rol!!,
                onCrear = {
                    // 👉 aquí luego navegas a CrearHostalView
                },
                onEditar = { hostal ->
                    // 👉 aquí luego navegas a EditarHostalView
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
