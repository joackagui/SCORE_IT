package com.example.scoreit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.scoreit.componentes.Partido
import com.example.scoreit.componentes.Campeonato

@Dao
interface PartidoDao {
    @Query("SELECT * FROM Partido")
    suspend fun obtenerTodosLosPartidos(): List<Partido>

    @Query("SELECT * FROM Partido WHERE id =:id")
    fun obtenerPorId(id: String): Partido

    @Query("SELECT * FROM Partido WHERE id =:id")
    fun obtenerPartidosPorId(id: String): MutableList<Partido>

    @Query("""
        SELECT c.diferenciaDosPuntos
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    fun obtenerCantidadDescansos(idPartido: String): Boolean

    @Query("""
        SELECT c.permisoDeRonda
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    fun obtenerSiHayRondas(idPartido: String): Boolean

    @Query("""
        SELECT c.diferenciaDosPuntos
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    fun obtenerSiHayDiferenciaDeDosPuntos(idPartido: String): Boolean

    @Query("""
        SELECT c.seJuegaPorPuntosMaximos
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    fun obtenerSiPartidoPorPuntos(idPartido: String): Boolean

    @Query("""
        SELECT c.seJuegaPorTiempoMaximo
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    fun obtenerSiPartidoPorTiempo(idPartido: String): Boolean

    @Query("""
        SELECT c.puntosParaGanar
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    fun obtenerPuntosParaGanar(idPartido: String): Int

    @Query("""
        SELECT c.siempreUnGanador
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    fun obtenerSiempreUnGanador(idPartido: String): Boolean

    @Update
    fun update(partido: Partido)

    @Insert
    fun insert(partido: Partido)

    @Insert
    fun insertarPartidos(partido: MutableList<Partido>)
}