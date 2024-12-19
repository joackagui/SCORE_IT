package com.example.scoreit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.scoreit.componentes.Usuario
import com.example.scoreit.componentes.Campeonato
import com.example.scoreit.componentes.Partido
import com.example.scoreit.componentes.Equipo

@Database(
    entities = [Usuario::class, Campeonato::class, Partido::class, Equipo::class],
    version = 2,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun campeonatoDao(): CampeonatoDao
    abstract fun partidoDao(): PartidoDao
    abstract fun equipoDao(): EquipoDao

    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this){
                val room = Room
                    .databaseBuilder(
                        context,
                        AppDataBase::class.java,
                        "Scoreit_database"
                    ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = room
                room
            }
        }
    }
}
