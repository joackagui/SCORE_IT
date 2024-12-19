package com.example.scoreit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.scoreit.componentes.Campeonato

@Dao
interface CampeonatoDao {
    @Query("SELECT * FROM Campeonato")
    suspend fun obtenerTodosLosCampeonatos(): MutableList<Campeonato>

    @Query("SELECT * FROM Campeonato WHERE id =:id")
    suspend fun obtenerPorId(id: String): Campeonato

    @Update
    suspend fun update(campeonato: Campeonato)

    @Insert
    suspend fun insert(campeonato: Campeonato)

    @Insert
    suspend fun insertarVariosCampeonatos(campeonato: List<Campeonato>)

    @Query("DELETE FROM Campeonato WHERE id =:id")
    suspend fun deleteById(id: Int)

}