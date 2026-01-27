package com.example.hostalapp1.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HostalViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HostalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HostalViewModel(application) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}
