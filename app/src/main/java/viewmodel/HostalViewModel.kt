package com.example.hostalapp1.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hostalapp1.data.remote.api.RetrofitClient
import com.example.hostalapp1.model.Hostal
import kotlinx.coroutines.launch



class HostalViewModel : ViewModel() {

    //Lista de hostales (local y api)
    private val _hostales = mutableStateListOf<Hostal>()
    val hostales: List<Hostal> = _hostales


    // Estados
    var cargando = mutableStateOf(false)
        private set

    var error = mutableStateOf<String?>(null)
        private set


    // crud local

    fun agregarHostal(hostal: Hostal) {
        _hostales.add(hostal)
    }

    fun eliminarHostal(hostal: Hostal) {
        _hostales.remove(hostal)
    }

    fun editarHostal(original: Hostal, editado: Hostal) {
        val index = _hostales.indexOf(original)
        if (index != -1) {
            _hostales[index] = editado
        }
    }
    // consumo de api

    fun cargarHostalesExternos() {
        viewModelScope.launch {
            cargando.value = true
            error.value = null

            try {
                val respuesta = RetrofitClient.apiService.obtenerHostalesExternos()

                _hostales.clear()
                _hostales.addAll(
                    respuesta.map {
                        Hostal(
                            nombre = it.title,
                            precio = "Desde $10.000"
                        )
                    }
                )


            } catch (e: Exception) {
                error.value = "Error al cargar hostales externos"
            } finally {
                cargando.value = false
            }
        }
    }
}
