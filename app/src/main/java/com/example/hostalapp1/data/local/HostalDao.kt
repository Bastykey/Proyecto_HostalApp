package com.example.hostalapp1.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hostalapp1.data.local.HostalEntity

@Dao
interface HostalDao {

    // Obtener todos los hostales
    @Query("SELECT * FROM hostales")
    suspend fun obtenerHostales(): List<HostalEntity>

    //  Insertar un hostal
    @Insert
    suspend fun insertarHostal(hostal: HostalEntity)

    // Actualizar un hostal
    @Update
    suspend fun actualizarHostal(hostal: HostalEntity)

    //  Eliminar un hostal
    @Delete
    suspend fun eliminarHostal(hostal: HostalEntity)

    // Eliminar todos (útil para pruebas)
    @Query("DELETE FROM hostales")
    suspend fun eliminarTodos()
}
