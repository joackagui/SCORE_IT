package com.example.scoreit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.scoreit.componentes.Equipo

@Dao
interface EquipoDao {
    @Query("SELECT * FROM Equipo")
    suspend fun obtenerTodosLosEquipos(): List<Equipo>

    @Query("SELECT * FROM Equipo WHERE id =:id")
    suspend fun obtenerPorId(id: String): Equipo

    @Update
    suspend fun update(equipo: Equipo)

    @Insert
    suspend fun insert(equipo: Equipo)

    @Insert
    suspend fun insertarEquipos(equipo: List<Equipo>)
}