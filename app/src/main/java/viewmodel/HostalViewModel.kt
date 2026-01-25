package com.example.hostalapp1.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.hostalapp1.model.Hostal

class HostalViewModel : ViewModel() {

    private val _hostales = mutableStateListOf<Hostal>()
    val hostales: List<Hostal> = _hostales

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
}
