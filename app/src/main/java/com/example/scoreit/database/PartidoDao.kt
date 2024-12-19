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
    suspend fun obtenerPorId(id: String): Partido

    @Query("SELECT * FROM Partido WHERE idCampeonato =:idCampeonato")
    suspend fun obtenerPartidosPorId(idCampeonato: String): MutableList<Partido>

    @Query("""
        SELECT c.cantidadDeDescansos
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    suspend fun obtenerCantidadDescansos(idPartido: String): Int

    @Query("""
        SELECT c.idaYVuelta
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    suspend fun obtenerSiIdeaYVuelta(idPartido: String): Boolean

    @Query("""
        SELECT c.permisoDeRonda
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    suspend fun obtenerSiHayRondas(idPartido: String): Boolean

    @Query("""
        SELECT c.diferenciaDosPuntos
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    suspend fun obtenerSiHayDiferenciaDeDosPuntos(idPartido: String): Boolean

    @Query("""
        SELECT c.seJuegaPorPuntosMaximos
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    suspend fun obtenerSiPartidoPorPuntos(idPartido: String): Boolean

    @Query("""
        SELECT c.seJuegaPorTiempoMaximo
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    suspend fun obtenerSiPartidoPorTiempo(idPartido: String): Boolean

    @Query("""
        SELECT c.tiempoDeJuego
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    suspend fun obtenerTiempoDelPartido(idPartido: String): Int

    @Query("""
        SELECT c.puntosParaGanar
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    suspend fun obtenerPuntosParaGanar(idPartido: String): Int

    @Query("""
        SELECT c.siempreUnGanador
        FROM Partido p
        INNER JOIN Campeonato c ON p.idCampeonato = c.id
        WHERE p.id = :idPartido
    """)
    suspend fun obtenerSiempreUnGanador(idPartido: String): Boolean

    @Update
    suspend fun update(partido: Partido)

    @Insert
    suspend fun insert(partido: Partido)

    @Insert
    suspend fun insertarPartidos(partido: MutableList<Partido>)
}