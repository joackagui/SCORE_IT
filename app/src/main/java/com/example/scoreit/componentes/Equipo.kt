package com.example.scoreit.componentes

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.io.Serializable

@Entity(
    foreignKeys = [ForeignKey(
        entity = Campeonato::class,
        parentColumns = ["id"],
        childColumns = ["idCampeonato"],
        onDelete = ForeignKey.CASCADE)
    ]
)

data class Equipo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val logo: String? = null,
    val nombre: String,
    var puntosFinales: Int = 0,
    var puntosInGame: Int = 0,
    var rondasAFavor: Int = 0,
    var partidosJugados: Int = 0,
    var partidosGanados: Int = 0,
    val idCampeonato: Int
): Serializable
