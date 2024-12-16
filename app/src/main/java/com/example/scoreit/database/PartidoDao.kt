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

    @Query("SELECT * FROM Partido WHERE id =:id")
    fun obtenerPartidosPorId(id: String): MutableList<Partido>

    @Update
    suspend fun update(partido: Partido)

    @Insert
    fun insert(partido: Partido)

    @Insert
    suspend fun insertarPartidos(partido: List<Partido>)
}