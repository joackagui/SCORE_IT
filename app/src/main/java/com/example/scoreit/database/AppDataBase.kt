package com.example.scoreit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.scoreit.componentes.Usuario
import com.example.scoreit.componentes.Campeonato
import com.example.scoreit.componentes.Partido
import com.example.scoreit.componentes.Equipo

@Database(
    entities = [Usuario::class, Campeonato::class, Partido::class, Equipo::class],
    version = 1
)
@TypeConverters(Converters::class)

abstract class AppDataBase: RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun campeonatoDao(): CampeonatoDao
    abstract fun partidoDao(): PartidoDao
    abstract fun equipoDao(): EquipoDao
}
