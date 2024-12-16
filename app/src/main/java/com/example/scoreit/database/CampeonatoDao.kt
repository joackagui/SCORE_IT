package com.example.scoreit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.scoreit.componentes.Campeonato

@Dao
interface CampeonatoDao {
    @Query("SELECT * FROM Campeonato")
    fun obtenerTodosLosCampeonatos(): List<Campeonato>

    @Query("SELECT * FROM Campeonato WHERE id =:id")
    fun obtenerPorId(id: String): Campeonato

    @Query("SELECT * FROM Campeonato WHERE idUsuario =:idUsuario")
    fun obtenerCampeonatosPorIdUsuario(idUsuario: String): MutableList<Campeonato>

    @Update
    fun update(campeonato: Campeonato)

    @Insert
    fun insert(campeonato: Campeonato)

}