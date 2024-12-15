package com.example.scoreit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.scoreit.componentes.Partido

@Dao
interface PartidoDao {
    @Query("SELECT * FROM Partido")
    suspend fun obtenerTodosLosPartidos(): List<Partido>

    @Query("SELECT * FROM Partido WHERE id =:id")
    suspend fun obtenerPorId(id: String): Partido

    @Update
    suspend fun update(partido: Partido)

    @Insert
    suspend fun insert(partido: Partido)

    @Insert
    suspend fun insertarPartidos(partido: List<Partido>)
}