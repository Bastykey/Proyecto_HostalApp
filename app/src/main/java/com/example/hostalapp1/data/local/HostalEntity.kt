package com.example.hostalapp1.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey

//  Representa la tabla "hostales" en SQLite
@Entity(tableName = "hostales")
data class HostalEntity(

    //  ID autogenerado por SQLite
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    //  Campos equivalentes al modelo Hostal
    val nombre: String,
    val precio: String

)
