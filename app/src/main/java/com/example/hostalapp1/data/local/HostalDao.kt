package com.example.hostalapp1.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hostalapp1.data.local.HostalEntity

//Operadores CRUD EN LA BD
@Dao
interface HostalDao {

    @Query("SELECT * FROM hostales")
    suspend fun obtenerHostales(): List<HostalEntity>

    @Insert
    suspend fun insertarHostal(hostal: HostalEntity)

    @Update
    suspend fun actualizarHostal(hostal: HostalEntity)

    @Delete
    suspend fun eliminarHostal(hostal: HostalEntity)

    @Query("DELETE FROM hostales")
    suspend fun eliminarTodos()
}
