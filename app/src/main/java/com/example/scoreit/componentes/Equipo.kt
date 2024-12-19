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
    val puntosFinales: Int = 0,
    var puntos: Int = 0,
    var rondasAFavor: Int = 0,
    var rondasEnContra: Int = 0,
    val partidosJugados: Int = 0,
    val idCampeonato: Int
): Serializable
