package com.example.hostalapp1.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//  Base de datos local de la app
@Database(
    entities = [HostalEntity::class],   // Tablas
    version = 1,                         // Versión inicial
    exportSchema = false
)
abstract class HostalDatabase : RoomDatabase() {

    // DAO que maneja las operaciones
    abstract fun hostalDao(): HostalDao

    companion object {

        //  Instancia única (singleton)
        @Volatile
        private var INSTANCE: HostalDatabase? = null

        fun getDatabase(context: Context): HostalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HostalDatabase::class.java,
                    "hostal_database"      // Nombre del archivo SQLite
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
