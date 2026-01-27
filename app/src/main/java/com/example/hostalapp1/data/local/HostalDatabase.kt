package com.example.hostalapp1.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//  Base de datos local de la app. SQLITE
@Database(
    entities = [HostalEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HostalDatabase : RoomDatabase() {

    // Aqui el DAO  maneja las operaciones
    abstract fun hostalDao(): HostalDao

    companion object {

        //  Instancia única
        @Volatile
        private var INSTANCE: HostalDatabase? = null

        fun getDatabase(context: Context): HostalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HostalDatabase::class.java,
                    "hostal_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
