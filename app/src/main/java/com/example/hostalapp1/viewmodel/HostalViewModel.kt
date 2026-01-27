package com.example.hostalapp1.viewmodel

import android.app.Application
import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hostalapp1.data.local.HostalDatabase
import com.example.hostalapp1.data.local.HostalEntity
import com.example.hostalapp1.data.remote.api.RetrofitClient
import com.example.hostalapp1.model.Hostal
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class HostalViewModel(application: Application) : AndroidViewModel(application) {

    // 🔹 Lista observable para Compose
    private val _hostales = mutableStateListOf<Hostal>()
    val hostales: List<Hostal> = _hostales

    // 🔹 Ubicación GPS
    var ubicacionActual = mutableStateOf<Location?>(null)
        private set

    // 🔹 Acceso a SQLite
    private val hostalDao =
        HostalDatabase.getDatabase(application).hostalDao()

    // 🔹 Estados
    var cargando = mutableStateOf(false)
        private set

    var error = mutableStateOf<String?>(null)
        private set

    // 🔹 Se ejecuta UNA SOLA VEZ
    init {
        error.value = null
        cargarHostalesLocales()
    }

    // =====================
    // GPS (recurso nativo)
    // =====================
    @SuppressLint("MissingPermission")
    fun obtenerUbicacion() {
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(getApplication())

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                ubicacionActual.value = location
            }
            .addOnFailureListener {
                // ❌ No rompemos la app
            }
    }

    // =====================
    // CRUD LOCAL (SQLite)
    // =====================
    fun agregarHostal(hostal: Hostal) {
        viewModelScope.launch {
            hostalDao.insertarHostal(
                HostalEntity(
                    nombre = hostal.nombre,
                    precio = hostal.precio
                )
            )
            cargarHostalesLocales()
        }
    }

    fun eliminarHostal(hostal: Hostal) {
        viewModelScope.launch {
            hostalDao.eliminarHostal(
                HostalEntity(
                    nombre = hostal.nombre,
                    precio = hostal.precio
                )
            )
            cargarHostalesLocales()
        }
    }

    fun editarHostal(original: Hostal, editado: Hostal) {
        viewModelScope.launch {
            hostalDao.eliminarHostal(
                HostalEntity(
                    nombre = original.nombre,
                    precio = original.precio
                )
            )
            hostalDao.insertarHostal(
                HostalEntity(
                    nombre = editado.nombre,
                    precio = editado.precio
                )
            )
            cargarHostalesLocales()
        }
    }

    private fun cargarHostalesLocales() {
        viewModelScope.launch {
            val lista = hostalDao.obtenerHostales()
            _hostales.clear()
            _hostales.addAll(
                lista.map {
                    Hostal(
                        nombre = it.nombre,
                        precio = it.precio
                    )
                }
            )
        }
    }

    // =====================
    // RETROFIT (SILENCIOSO)
    // =====================
    fun cargarHostalesExternos() {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitClient.apiService.obtenerHostalesExternos()

                respuesta.forEach {
                    hostalDao.insertarHostal(
                        HostalEntity(
                            nombre = it.title,
                            precio = "Desde $10.000"
                        )
                    )
                }

                cargarHostalesLocales()

            } catch (e: Exception) {
                // ❌ NO hacemos nada
                // 👉 La app NO se cae aunque falle la API
            }
        }
    }

    // =====================
    // GPS + API
    // =====================
    @SuppressLint("MissingPermission")
    fun cargarHostalesSegunUbicacion() {
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(getApplication())

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    ubicacionActual.value = location
                    // 🔹 GPS OK → llamamos a la API
                    cargarHostalesExternos()
                } else {
                    // ❌ No hacemos nada
                }
            }
            .addOnFailureListener {
                // ❌ No rompemos la app
            }
    }
}
