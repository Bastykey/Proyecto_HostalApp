package com.example.hostalapp1.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hostalapp1.data.local.HostalDatabase
import com.example.hostalapp1.data.local.HostalEntity
import com.example.hostalapp1.data.remote.api.RetrofitClient
import com.example.hostalapp1.model.Hostal
import kotlinx.coroutines.launch
import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.LocationServices


class HostalViewModel(application: Application) : AndroidViewModel(application) {

    // Lista de hostales (local y api)
    private val _hostales = mutableStateListOf<Hostal>()
    val hostales: List<Hostal> = _hostales
    var ubicacionActual = mutableStateOf<Location?>(null)
        private set
    //aqui esta el gps
    @SuppressLint("MissingPermission")
    fun obtenerUbicacion() {
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(getApplication())

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                ubicacionActual.value = location
            }
            .addOnFailureListener {
                error.value = "No se pudo obtener la ubicación"
            }
    }


    //  Acceso a SQLite
    private val hostalDao =
        HostalDatabase.getDatabase(application).hostalDao()

    // Estados
    var cargando = mutableStateOf(false)
        private set

    var error = mutableStateOf<String?>(null)
        private set

    //  Se ejecuta una sola vez al crear el ViewModel
    init {
        error.value = null
        cargarHostalesLocales()   // 🔹 Cargar SQLite al iniciar
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

    // 🔹 Cargar hostales desde SQLite
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
    // 🔹 Prueba rápida para verificar SQLite
    fun insertarHostalPrueba() {
        viewModelScope.launch {
            hostalDao.insertarHostal(
                HostalEntity(
                    nombre = "Hostal Demo",
                    precio = "Desde $12.000"
                )
            )
        }
    }

    // =====================
    // CONSUMO DE API
    // =====================

    fun cargarHostalesExternos() {
        viewModelScope.launch {
            cargando.value = true
            error.value = null

            try {
                val respuesta = RetrofitClient.apiService.obtenerHostalesExternos()

                // 🔹 Guardar hostales externos en SQLite
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
                error.value = "Error al cargar hostales externos"
            } finally {
                cargando.value = false
            }
        }
    }
}

