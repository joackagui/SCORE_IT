package com.example.scoreit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.scoreit.componentes.Campeonato

@Dao
interface CampeonatoDao {
    @Query("SELECT * FROM Campeonato")
    suspend fun obtenerTodosLosCampeonatos(): List<Campeonato>

    @Query("SELECT * FROM Campeonato WHERE id =:id")
    suspend fun obtenerPorId(id: String): Campeonato

    @Query("SELECT * FROM Campeonato WHERE idUsuario =:idUsuario")
    suspend fun obtenerCampeonatosPorIdUsuario(idUsuario: String): MutableList<Campeonato>

    @Update
    suspend fun update(campeonato: Campeonato)

    @Insert
    suspend fun insert(campeonato: Campeonato)

}