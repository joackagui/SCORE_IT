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
    var puntosFinales: String = "0",
    var puntosInGame: String = "0",
    var rondasAFavor: String = "0",
    var partidosJugados: String = "0",
    var partidosGanados: String = "0",
    val idCampeonato: Int
): Serializable
