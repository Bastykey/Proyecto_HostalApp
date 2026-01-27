package com.example.hostalapp1.auth

import com.example.hostalapp1.model.Rol

// Credenciales para el admin
private const val ADMIN_USUARIO = "Admin"
private const val ADMIN_CONTRASEÑA = "Admin1"

// Función que valida el login y retorna el rol
fun validarCredenciales(usuario: String, contraseña: String): Rol? {
    return when {
        usuario.isBlank() || contraseña.isBlank() -> null


        usuario == ADMIN_USUARIO && contraseña == ADMIN_CONTRASEÑA -> Rol.Admin

        else -> Rol.Cliente
    }
}




